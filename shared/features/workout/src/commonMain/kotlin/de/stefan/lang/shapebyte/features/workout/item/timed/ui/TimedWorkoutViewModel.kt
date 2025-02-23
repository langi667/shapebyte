package de.stefan.lang.shapebyte.features.workout.item.timed.ui

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.coreutils.progress.Progress
import de.stefan.lang.designsystem.ColorDescriptor
import de.stefan.lang.foundationCore.audio.AudioPlaying
import de.stefan.lang.foundationCore.audio.AudioResource
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.foundationCore.loadstate.asResultFlow
import de.stefan.lang.foundationCore.stringformatter.DateTimeStringFormatter
import de.stefan.lang.foundationUI.buttons.ButtonState
import de.stefan.lang.foundationUI.viewmodel.BaseViewModel
import de.stefan.lang.foundationUI.viewmodel.UIState
import de.stefan.lang.shapebyte.features.workout.data.Workout
import de.stefan.lang.shapebyte.features.workout.data.WorkoutType
import de.stefan.lang.shapebyte.features.workout.item.core.data.ExerciseExecutionInfo
import de.stefan.lang.shapebyte.features.workout.item.core.data.IntervalExerciseInfo
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecutionState
import de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecutionData
import de.stefan.lang.shapebyte.features.workout.quick.domain.QuickWorkoutForIdUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class TimedWorkoutViewModel(
    private val quickWorkoutForIdUseCase: QuickWorkoutForIdUseCase,
    private val itemsExecutionBuilder: ItemsExecutionBuilder,
    private val dateStringFormatter: DateTimeStringFormatter,
    private val audioPlayer: AudioPlaying,
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {

    enum class LaunchState {
        Idle,
        Running,
        Pause,
        Finished,

        ;

        // TODO: Test
        val isRunning: Boolean get() = this == Running
    }

    private val _state = MutableStateFlow<UIState>(UIState.Idle)
    override val state: StateFlow<UIState> = _state

    var workout: Workout? = null
        private set

    var launchState: LaunchState = LaunchState.Idle
        private set

    val isRunning: Boolean get() = launchState.isRunning

    private var remainingTotal: Duration = Duration.ZERO
    private var elapsedTotal: Duration = Duration.ZERO
    private var itemsExecution: ItemsExecution? = null
    private var loadWorkoutJob: Job? = null

    fun load(workoutId: Int) {
        _state.value = UIState.Loading

        loadWorkoutJob = scope.launch {
            quickWorkoutForIdUseCase
                .invoke(workoutId)
                .asResultFlow()
                .collectLatest {
                    when (it) {
                        is LoadState.Success -> {
                            handleWorkoutDataLoaded(it.data)
                        }

                        is LoadState.Error -> {
                            /* TODO: Handle */
                        }
                    }
                    stopLoadingWorkoutData()
                }
        }
    }

    fun start() {
        if (isRunning) {
            logW("Cannot start, already running")
            return
        }

        val workoutNotNull = workout ?: run {
            logE("Cannot start, workout is null")
            return
        }

        itemsExecution = itemsExecutionBuilder.buildWith(workoutNotNull.type)
        startWorkout()
    }

    fun pauseOrStartWorkout() {
        itemsExecution?.pauseOrStart(scope)
    }

    fun stop() {
        itemsExecution?.stop()
    }

    private fun stopLoadingWorkoutData() {
        loadWorkoutJob?.cancel()
        loadWorkoutJob = null
    }

    private fun startWorkout() {
        val itemsExecutionNotNull = itemsExecution ?: run {
            logE("Cannot start, ItemsExecution is null")
            return
        }

        audioPlayer.play(AudioResource("ding.mp3"))

        val prevState = launchState
        launchState = LaunchState.Running
        updateWorkoutInitialTimes()

        scope.launch {
            if (prevState == LaunchState.Finished) {
                createViewData(exerciseProgress = Progress.ZERO)
            }

            itemsExecutionNotNull
                .state
                .collectLatest {
                    when (it) {
                        is ItemsExecutionState.Started -> {
                            handleWorkoutStarted()
                        }

                        is ItemsExecutionState.Running -> {
                            handleWorkoutRunning(it)
                        }

                        is ItemsExecutionState.Paused -> {
                            handleWorkoutPaused()
                        }

                        is ItemsExecutionState.Finished -> {
                            finishWorkout()
                        }

                        else -> {
                            logD("Ignoring state: $it")
                        }
                    }
                }
        }

        itemsExecutionNotNull.start(scope)
    }

    private fun handleWorkoutPaused() {
        launchState = LaunchState.Pause
        val currState = this._state.value.dataOrNull<TimedWorkoutViewData>() ?: run {
            logW("Cannot perform updateUI, TimedWorkoutViewData is null")
            return
        }

        updateUIStateWithData(currState.copy(launchState = launchState))
    }

    private fun handleWorkoutStarted() {
        updateUIStateWithData(createViewData())
    }

    private fun handleWorkoutRunning(workoutState: ItemsExecutionState.Running) {
        launchState = LaunchState.Running

        val currState = this._state.value.dataOrNull<TimedWorkoutViewData>() ?: run {
            logW("Cannot perform updateUI, TimedWorkoutViewData is null")
            return
        }

        val nextState = when (val currExerciseState = workoutState.itemState) {
            is ItemExecutionState.SetStarted -> {
                updateWorkoutTimes(currExerciseState)

                viewDataForExerciseSetStarted(
                    workoutState = workoutState,
                    exerciseState = currExerciseState,
                )
            }

            is ItemExecutionState.SetRunning -> {
                viewDataForExerciseSetRunning(
                    workoutState = workoutState,
                    exerciseState = currExerciseState,
                )
            }

            is ItemExecutionState.SetFinished -> {
                updateWorkoutTimes(currExerciseState)
                viewDataForExerciseSetFinished(currExerciseState)
            }

            else -> {
                currState
            }
        }

        updateUIStateWithData(nextState)
    }

    private fun updateWorkoutTimes(state: ItemExecutionState.Running<*>) {
        val setData = state.setData as? TimedItemExecutionData ?: return

        elapsedTotal += setData.setTimePassed
        remainingTotal -= setData.setTimePassed
    }

    private fun viewDataForExerciseSetStarted(
        workoutState: ItemsExecutionState.Running,
        exerciseState: ItemExecutionState.SetStarted<*>,
    ): TimedWorkoutViewData {
        val data = exerciseState.setData as? TimedItemExecutionData ?: run {
            logW("Cannot perform viewDataForExerciseSetStarted, TimedItemExecutionData is null")
            return TimedWorkoutViewData()
        }

        return createViewData(
            setDuration = data.setDuration.inWholeMilliseconds.toInt(),
            exerciseTimeRemaining = ceil(data.totalTimeRemaining.toDouble(DurationUnit.SECONDS)).toInt(),
            exercise = exerciseState.item,
            exerciseProgress = workoutState.totalProgress,
        )
    }

    private fun viewDataForExerciseSetRunning(
        workoutState: ItemsExecutionState.Running,
        exerciseState: ItemExecutionState.SetRunning<*>,
    ): TimedWorkoutViewData {
        val data = exerciseState.setData as? TimedItemExecutionData ?: run {
            logW("Cannot perform viewDataForExerciseSetRunning, TimedItemExecutionData is null")
            return TimedWorkoutViewData()
        }

        return createViewData(
            setDuration = data.setDuration.inWholeMilliseconds.toInt(),
            exerciseTimeRemaining = ceil(data.totalTimeRemaining.toDouble(DurationUnit.SECONDS)).toInt(),
            exercise = exerciseState.item,
            exerciseProgress = workoutState.totalProgress,
        )
    }

    private fun viewDataForExerciseSetFinished(
        exerciseState: ItemExecutionState.SetFinished<*>,
    ): TimedWorkoutViewData {
        val data = exerciseState.setData as? TimedItemExecutionData ?: run {
            logW("Cannot perform viewDataForExerciseSetFinished, TimedItemExecutionData is null")
            return TimedWorkoutViewData()
        }

        return createViewData(
            exerciseTimeRemaining = ceil(data.totalTimeRemaining.toDouble(DurationUnit.SECONDS)).toInt(),
            setDuration = data.setDuration.inWholeMilliseconds.toInt(),
            exercise = exerciseState.item,
            exerciseProgress = exerciseState.totalProgress,
        )
    }

    private fun handleWorkoutDataLoaded(workout: Workout) {
        this.workout = workout

        updateWorkoutInitialTimes()
        updateUIStateWithData(createViewData())
    }

    private fun updateWorkoutInitialTimes() {
        val workoutNotNull = this.workout ?: run {
            logE("Unable to perform updateWorkoutInitialTimes, workout is null")
            return
        }

        val workoutType = workoutNotNull.type as? WorkoutType.Timed.Interval ?: run {
            logE(
                "Unable to perform updateWorkoutInitialTimes, expected workout type to be " +
                    "WorkoutType.Timed.Interval, but got ${workoutNotNull.type}",
            )
            return
        }

        this.elapsedTotal = Duration.ZERO
        this.remainingTotal = workoutType.secondsTotal.seconds
    }

    private fun createViewData(
        setDuration: Int = 0,
        exerciseTimeRemaining: Int = 0,
        exercise: Item? = null,
        exerciseProgress: Progress = Progress.ZERO,
    ): TimedWorkoutViewData {
        val defaultColor = ColorDescriptor.Background
        val color = if (exercise is ExerciseExecutionInfo) {
            when (exercise.intervalExerciseInfo) {
                IntervalExerciseInfo.HIGH -> {
                    ColorDescriptor.Primary
                }

                IntervalExerciseInfo.LOW -> {
                    ColorDescriptor.InversePrimary
                }

                else -> {
                    defaultColor
                }
            }
        } else {
            defaultColor
        }

        val remainingSeconds = ceil((remainingTotal).toDouble(DurationUnit.SECONDS)).toInt()
        val elapsedSeconds = ceil(elapsedTotal.toDouble(DurationUnit.SECONDS)).toInt()

        return TimedWorkoutViewData(
            title = workout?.name ?: "",
            remainingTotal = dateStringFormatter.formatSecondsToString(remainingSeconds),
            setDuration = setDuration,
            elapsedTotal = dateStringFormatter.formatSecondsToString(elapsedSeconds),
            remaining = dateStringFormatter.formatSecondsToString(exerciseTimeRemaining),
            playButtonState = if (isRunning) {
                ButtonState.Hidden
            } else {
                ButtonState.Visible { this.start() }
            },
            pauseButtonState = if (isRunning) {
                ButtonState.Visible { this.pauseOrStartWorkout() }
            } else {
                ButtonState.Hidden
            },
            stopButtonState = if (isRunning) {
                ButtonState.Visible { this.stop() }
            } else {
                ButtonState.Hidden
            },
            exercise = exercise,
            progressTotal = exerciseProgress.value,
            backgroundColor = color,
            launchState = this.launchState,
        )
    }

    private fun updateUIStateWithData(viewData: TimedWorkoutViewData) {
        _state.value = UIState.Data(viewData)
    }

    private fun finishWorkout() {
        launchState = LaunchState.Finished
        remainingTotal = Duration.ZERO
        elapsedTotal = Duration.ZERO

        updateUIStateWithData(
            createViewData(exerciseProgress = Progress.COMPLETE),
        )
    }
}
