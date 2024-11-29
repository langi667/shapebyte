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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        // TODO: Paused
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

    private var remainingTotal: Int = 0
    private var elapsedTotal: Int = 0
    private var itemsExecution: ItemsExecution? = null

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

                    cancel()
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
        startItemsExecution()
    }

    private fun startItemsExecution() {
        val itemsExecutionNotNull = itemsExecution ?: run {
            logE("Cannot start, ItemsExecution is null")
            return
        }

        val prevState = launchState
        launchState = LaunchState.Running
        updateWorkoutInitialTimes()

        logD("Start item execution")

        scope.launch {
            if (prevState == LaunchState.Finished) {
                updateDataState(progress = Progress.ZERO)
            }

            itemsExecutionNotNull
                .state
                .collectLatest {
                    when (it) {
                        is ItemsExecutionState.Running -> {
                            val itemState = it.itemState
                            updateWithItemExecutionState(it.nextTotalProgress, itemState)
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

        itemsExecutionNotNull.start(scope)
    }

    private fun updateWithItemExecutionState(
        progressTotal: Progress,
        itemState: ItemExecutionState.Running<*>,
    ) {
        updateWithTimedData(
            item = itemState.item,
            data = itemState.setData as? TimedItemExecutionData,
            progressTotal = progressTotal,
        )
    }

    private fun updateWithTimedData(
        item: Item?,
        data: TimedItemExecutionData?,
        progressTotal: Progress,
    ) {
        if (data == null) {
            logW("Cannot perform updateWithTimedData, TimedItemExecutionData is null")
            return
        }

        updateDataState(
            secPassed = data.setTimePassed.inWholeSeconds.toInt(),
            setRemaining = data.totalTimeRemaining.inWholeSeconds.toInt(),
            setDuration = data.setDuration.inWholeMilliseconds.toInt(),
            item = item,
            progress = progressTotal,
        )
    }

    private fun handleWorkoutLoaded(workout: Workout) {
        this.workout = workout

        updateWorkoutInitialTimes()
        updateDataState()
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

        this.elapsedTotal = 0
        this.remainingTotal = workoutType.secondsTotal
    }

    private fun updateDataState(
        secPassed: Int = 0,
        setRemaining: Int = 0,
        setDuration: Int = 0,
        item: Item? = null,
        progress: Progress = Progress.ZERO,
    ) {
        elapsedTotal += secPassed
        remainingTotal -= secPassed

        val defaultColor = ColorDescriptor.Background
        val color = if (item is ExerciseExecutionInfo) {
            when (item.intervalExerciseInfo) {
                IntervalExerciseInfo.HIGH -> { ColorDescriptor.Primary }
                IntervalExerciseInfo.LOW -> { ColorDescriptor.InversePrimary }
                else -> { defaultColor }
            }
        } else { defaultColor }

        _state.value = UIState.Data(
            TimedWorkoutViewData(
                title = workout?.name ?: "",
                remainingTotal = dateStringFormatter.formatSecondsToString(remainingTotal),
                setDuration = setDuration,
                elapsedTotal = dateStringFormatter.formatSecondsToString(elapsedTotal),
                remaining = dateStringFormatter.formatSecondsToString(setRemaining),
                playButtonState = if (isRunning) {
                    ButtonState.Hidden
                } else {
                    ButtonState.Visible { this.start() }
                },
                pauseButtonState = if (isRunning) {
                    ButtonState.Visible { /* TODO: pause action */ }
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
            ),
        )
    }

    private fun finish() {
        launchState = LaunchState.Finished
        remainingTotal = 0
        elapsedTotal = 0

        updateDataState(progress = Progress.COMPLETE)
    }
}
