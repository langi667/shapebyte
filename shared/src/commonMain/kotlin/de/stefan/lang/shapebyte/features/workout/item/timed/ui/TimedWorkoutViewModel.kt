package de.stefan.lang.shapebyte.features.workout.item.timed.ui

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.core.data.WorkoutType
import de.stefan.lang.shapebyte.features.workout.item.core.data.ExerciseExecutionInfo
import de.stefan.lang.shapebyte.features.workout.item.core.data.IntervalExerciseInfo
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecutionState
import de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecutionData
import de.stefan.lang.shapebyte.features.workout.quick.domain.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.loading.data.asResultFlow
import de.stefan.lang.shapebyte.shared.viewmodel.ui.BaseViewModel
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.buttons.ButtonState
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import de.stefan.lang.shapebyte.utils.designsystem.data.ColorDescriptor
import de.stefan.lang.shapebyte.utils.logging.Logging
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
    private var workoutExecution: ItemsExecution? = null

    fun update(workoutId: Int) {
        _state.value = UIState.Loading

        scope.launch {
            quickWorkoutForIdUseCase
                .invoke(workoutId)
                .asResultFlow()
                .collectLatest {
                    when (it) {
                        is LoadState.Success -> { handleWorkoutLoaded(it.data) }
                        is LoadState.Error -> { /* TODO: Handle */ }
                    }
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

        workoutExecution = itemsExecutionBuilder.buildWith(workoutNotNull.type)
        startWorkoutExecution()
    }

    fun pauseOrStart() {
        workoutExecution?.pauseOrStart(scope)
    }

    private fun startWorkoutExecution() {
        val workoutExecutionNotNull = workoutExecution ?: run {
            logE("Cannot start, workoutExecution is null")
            return
        }

        val prevState = launchState
        launchState = LaunchState.Running
        updateWorkoutInitialTimes()

        scope.launch {
            if (prevState == LaunchState.Finished) {
                createViewData(progress = Progress.ZERO)
            }

            workoutExecutionNotNull
                .state
                .collectLatest {
                    when (it) {
                        is ItemsExecutionState.Running -> {
                            updateWithStateRunning(it)
                        }

                        is ItemsExecutionState.Paused -> {
                            updateWithStatePaused()
                        }

                        is ItemsExecutionState.Finished -> {
                            finish()
                        }

                        else -> {
                            logD("Ignoring state: $it")
                        }
                    }
                }
        }

        workoutExecutionNotNull.start(scope)
    }

    private fun updateWithStatePaused() {
        val currState = this._state.value.dataOrNull<TimedWorkoutViewData>() ?: run {
            logW("Cannot perform updateUI, TimedWorkoutViewData is null")
            return
        }

        this.launchState = LaunchState.Pause
        updateUIStateWithData(currState.copy(launchState = LaunchState.Pause))
    }

    private fun updateWithStateRunning(workoutState: ItemsExecutionState.Running) {
        val currState = state.value.dataOrNull<TimedWorkoutViewData>() ?: run {
            logW("Cannot perform updateUI, TimedWorkoutViewData is null")
            return
        }

        launchState = LaunchState.Running

        val nextState = when (val currItemState = workoutState.itemState) {
            is ItemExecutionState.SetStarted -> {
                viewDataForExerciseSetStarted(currItemState)
                // currState.copy(progressTotal = workoutState.totalProgress.value, launchState = launchState)
            }

            is ItemExecutionState.SetRunning -> {
                currState.copy(progressTotal = workoutState.totalProgress.value, launchState = launchState)
            }

            is ItemExecutionState.SetFinished -> {
                viewDataForExerciseSetFinished(currItemState)
            }
            else -> {
                currState
            }
        }

        _state.value = UIState.Data(nextState)
    }

    private fun viewDataForExerciseSetStarted(
        itemState: ItemExecutionState.SetStarted<*>,
    ): TimedWorkoutViewData {
        val data = itemState.setData as? TimedItemExecutionData ?: run {
            logW("Cannot perform updateUI, TimedItemExecutionData is null")
            return TimedWorkoutViewData()
        }

        return createViewData(
            timePassed = data.setTimePassed,
            setRemaining = ceil(data.totalTimeRemaining.toDouble(DurationUnit.SECONDS)).toInt(),
            setDuration = data.setDuration.inWholeMilliseconds.toInt(),
            item = itemState.item,
            progress = itemState.totalProgress,
        )
    }

    private fun viewDataForExerciseSetFinished(
        itemState: ItemExecutionState.SetFinished<*>,
    ): TimedWorkoutViewData {
        val data = itemState.setData as? TimedItemExecutionData ?: run {
            logW("Cannot perform updateUI, TimedItemExecutionData is null")
            return TimedWorkoutViewData()
        }

        return createViewData(
            timePassed = data.setTimePassed,
            setRemaining = ceil(data.totalTimeRemaining.toDouble(DurationUnit.SECONDS)).toInt(),
            setDuration = data.setDuration.inWholeMilliseconds.toInt(),
            item = itemState.item,
            progress = itemState.totalProgress,
        )
    }

    private fun handleWorkoutLoaded(workout: Workout) {
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
                    "WorkoutType.Timed.Interval, but got ${workoutNotNull.type }",
            )
            return
        }

        this.elapsedTotal = Duration.ZERO
        this.remainingTotal = workoutType.secondsTotal.seconds
    }

    private fun createViewData(
        timePassed: Duration = Duration.ZERO,
        setRemaining: Int = 0,
        setDuration: Int = 0,
        item: Item? = null,
        progress: Progress = Progress.ZERO,
    ): TimedWorkoutViewData {
        elapsedTotal += timePassed
        remainingTotal -= timePassed

        val defaultColor = ColorDescriptor.Background
        val color = if (item is ExerciseExecutionInfo) {
            when (item.intervalExerciseInfo) {
                IntervalExerciseInfo.HIGH -> { ColorDescriptor.Primary }
                IntervalExerciseInfo.LOW -> { ColorDescriptor.InversePrimary }
                else -> { defaultColor }
            }
        } else { defaultColor }

        val remainingSeconds = ceil((remainingTotal).toDouble(DurationUnit.SECONDS)).toInt()
        val elapsedSeconds = ceil(elapsedTotal.toDouble(DurationUnit.SECONDS)).toInt()

        return TimedWorkoutViewData(
            title = workout?.name ?: "",
            remainingTotal = dateStringFormatter.formatSecondsToString(remainingSeconds),
            setDuration = setDuration,
            elapsedTotal = dateStringFormatter.formatSecondsToString(elapsedSeconds),
            remaining = dateStringFormatter.formatSecondsToString(setRemaining),
            playButtonState = if (isRunning) {
                ButtonState.Hidden
            } else {
                ButtonState.Visible { this.start() }
            },
            pauseButtonState = if (isRunning) {
                ButtonState.Visible { this.pauseOrStart() }
            } else {
                ButtonState.Hidden
            },
            stopButtonState = if (isRunning) {
                ButtonState.Visible { /* TODO: stop action */ }
            } else {
                ButtonState.Hidden
            },
            item = item,
            progressTotal = progress.value,
            backgroundColor = color,
            launchState = this.launchState,
        )
    }

    private fun updateUIStateWithData(viewData: TimedWorkoutViewData) {
        _state.value = UIState.Data(viewData)
    }

    private fun finish() {
        launchState = LaunchState.Finished
        remainingTotal = Duration.ZERO
        elapsedTotal = Duration.ZERO

        updateUIStateWithData(
            createViewData(progress = Progress.COMPLETE),
        )
    }
}
