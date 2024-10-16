package de.stefan.lang.shapebyte.features.workout.domain.sethandler

import de.stefan.lang.shapebyte.features.workout.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.data.ItemSetData
import de.stefan.lang.shapebyte.features.workout.data.ItemSetState
import de.stefan.lang.shapebyte.features.workout.domain.ItemSetHandling
import de.stefan.lang.shapebyte.utils.CountdownTimer
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
// TODO: maybe stop/cancel required
class TimedItemSetHandler(
    private val logger: Logging,
    private val timer: CountdownTimer,
) : ItemSetHandling {
    var itemSet: ItemSet.Timed? = null
        private set

    val timerTick: Duration = 1.seconds

    private val _stateFlow = MutableStateFlow<ItemSetState>(ItemSetState.Idle)
    override val stateFlow: StateFlow<ItemSetState> = _stateFlow

    private val className = TimedItemSetHandler::class.simpleName ?: "TimedItemSetHandler"
    private val tag = className
    private var timerJob: Job? = null

    override fun start(set: ItemSet, scope: CoroutineScope) {
        if (!stateFlow.value.isStopped) {
            logger.d(tag, "Cannot start a new set while another one is running")
            return
        }

        when (set) {
            set as ItemSet.Timed -> startTimedSet(set, scope)
            else -> {
                logger.e(tag, "Unsupported set type for $className: ${set::class.simpleName}")
                return
            }
        }
    }

    override fun pause() {
        if (stateFlow.value is ItemSetState.Running) {
            timer.pause()
        }
    }

    override fun resume(resumeScope: CoroutineScope) {
        if (stateFlow.value is ItemSetState.Paused) {
            timer.start(resumeScope)
            startTimer(resumeScope)
        } else {
            logger.w(tag, "Cannot resume a set that is not paused")
        }
    }

    private fun startTimedSet(set: ItemSet.Timed, scope: CoroutineScope) {
        stopTimer()

        this.itemSet = set
        timer.setup(set.duration, timerTick)
        startTimer(scope)
    }

    private fun startTimer(
        scope: CoroutineScope,
    ) {
        timerJob?.cancel()
        timerJob = scope.launch {
            timer.start(
                scope = this,
            ).collect { timerState ->
                handleTimerState(timerState)

                if (!coroutineContext.isActive || stateFlow.value is ItemSetState.Finished) {
                    timerJob?.cancel()
                }

                yield()
            }
        }
    }

    private fun handleTimerState(timerState: CountdownTimer.State) {
        val state = timerStateToItemSetState(timerState)
        when (state) {
            is ItemSetState.Finished -> {
                timer.stop()
            }

            else -> { /* No Op */ }
        }

        _stateFlow.value = state
    }

    private fun timerStateToItemSetState(timerState: CountdownTimer.State): ItemSetState {
        val state = when (timerState) {
            is CountdownTimer.State.Idle -> ItemSetState.Idle
            is CountdownTimer.State.Running -> {
                if (timerState.isFirstRun) {
                    mapTimerStateStarted(timerState)
                } else {
                    mapTimerStateRunning(timerState)
                }
            }

            is CountdownTimer.State.Paused -> {
                mapTimerStatePaused(timerState)
            }

            is CountdownTimer.State.Stopped -> {
                mapTimerStateStopped(timerState)
            }
        }

        logger.d(tag, "timerState: $timerState  to TimedItemSetState: $state")
        return state
    }

    private fun stopTimer() {
        timer.stop()
    }

    // TODO: check if a canceled state might be needed
    @Suppress("unused")
    private fun mapTimerStateStopped(
        timerState: CountdownTimer.State.Stopped,
    ) = ItemSetState.Finished(
        ItemSetData.Timed(
            timerState.elapsed,
            timerState.duration,
            timerState.progress,
            timerState.nextProgress(timerTick),
        ),
    )

    private fun mapTimerStatePaused(
        timerState: CountdownTimer.State.Paused,
    ) = ItemSetState.Paused(
        ItemSetData.Timed(
            timerState.elapsed,
            timerState.duration,
            timerState.progress,
            timerState.nextProgress(timerTick),
        ),
    )

    private fun mapTimerStateStarted(
        timerState: CountdownTimer.State.Running,
    ) = ItemSetState.Started(
        ItemSetData.Timed(
            timerState.elapsed,
            timerState.duration,
            timerState.progress,
            timerState.nextProgress(timerTick),
        ),
    )

    private fun mapTimerStateRunning(
        timerState: CountdownTimer.State.Running,
    ): ItemSetState {
        val state = if (timerState.elapsed == 0.seconds) {
            ItemSetState.Started(
                ItemSetData.Timed(
                    timePassed = timerState.elapsed,
                    timeRemaining = timerState.duration,
                    progress = timerState.progress,
                    nextProgress = timerState.nextProgress(timerTick),
                ),
            )
        } else {
            ItemSetState.Running(
                ItemSetData.Timed(
                    timePassed = timerState.elapsed,
                    timeRemaining = timerState.duration,
                    progress = timerState.progress,
                    nextProgress = timerState.nextProgress(timerTick),
                ),
            )
        }

        return state
    }
}
