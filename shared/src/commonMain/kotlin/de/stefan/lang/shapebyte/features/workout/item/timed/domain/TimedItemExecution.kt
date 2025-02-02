package de.stefan.lang.shapebyte.features.workout.item.timed.domain

import de.stefan.lang.core.logging.Logging
import de.stefan.lang.core.progress.Progress
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.core.data.None
import de.stefan.lang.shapebyte.features.workout.item.core.data.sumSeconds
import de.stefan.lang.shapebyte.features.workout.item.core.data.sumSecondsTo
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.item.core.domain.TimedItemExecuting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class TimedItemExecution(
    override val item: Item,
    override val sets: List<ItemSet.Timed.Seconds>,
    override val logger: Logging,
) : TimedItemExecuting {

    companion object {
        fun countdown(seconds: UInt): TimedItemExecution {
            val sets = List(seconds.toInt()) {
                ItemSet.Timed.Seconds(1)
            }

            return DPI.createTimedItemExecution(None, sets)
        }
    }

    private val _state: MutableStateFlow<ItemExecutionState<TimedItemExecutionData>> =
        MutableStateFlow(
            ItemExecutionState.Idle,
        )

    override val state: StateFlow<ItemExecutionState<TimedItemExecutionData>> get() = _state

    private var currJob: Job? = null
    private var currSetIndex: Int = 0
    private var pauseExecution: Boolean = false
    private var resumeAtMS: Duration = Duration.ZERO

    override fun start(scope: CoroutineScope): Boolean {
        if (isRunning) {
            logE("Cannot start a new set while another one is running")
            return false
        }

        val resumeSetAt: Duration
        if (!isPaused) {
            currSetIndex = 0
            resumeSetAt = Duration.ZERO
            _state.value = ItemExecutionState.Started(item)
        } else {
            pauseExecution = false
            resumeSetAt = resumeAtMS
            resumeAtMS = Duration.ZERO
        }

        invalidateJob()
        currJob = startSets(scope, isPaused, resumeSetAt)
        return true
    }

    override fun pause(): Boolean {
        if (!isRunning) {
            logE("Cannot pause a set that is not running")
            return false
        }

        pauseExecution = true
        (_state.value as? ItemExecutionState.Running<TimedItemExecutionData>)?.let {
            logD("progress on pause ${it.totalProgress}")
            resumeAtMS = it.setData.setTimePassed
        }

        _state.value = ItemExecutionState.Paused(item)
        invalidateJob()

        return true
    }

    override fun stop(): Boolean {
        if (!isRunning) {
            logE("Cannot pause a set that is not running")
            return false
        }

        invalidateJob()
        _state.value = ItemExecutionState.Finished(item, false)
        return true
    }

    private fun startSets(
        scope: CoroutineScope,
        afterPause: Boolean = false,
        resumeCurrentSet: Duration = Duration.ZERO,
    ) = scope.launch {
        val totalTime = sets.sumSeconds()

        var resumeAt = resumeCurrentSet
        var resumedAfterPause = afterPause

        while (currSetIndex <= sets.lastIndex) {
            val set = sets[currSetIndex]

            var totalTimePassed: Duration = if (currSetIndex == 0) {
                Duration.ZERO
            } else {
                sets.sumSecondsTo(currSetIndex)
            } + resumeAt

            var totalTimeRemaining = totalTime - totalTimePassed
            if (pauseExecution) {
                return@launch
            }

            if (!resumedAfterPause) {
                val totalProgress = Progress.with(totalTimePassed, totalTime)
                _state.value = ItemExecutionState.SetStarted(
                    item = item,
                    set = set,
                    progress = Progress.ZERO,
                    totalProgress = totalProgress,
                    setData = TimedItemExecutionData(
                        setDuration = set.seconds,
                        setTimePassed = resumeAt,
                        totalTimePassed = totalTimePassed,
                        totalTimeRemaining = totalTimeRemaining,
                        totalDuration = totalTime,
                    ),
                )
            }

            resumedAfterPause = false // flag is reset because should only be called on first run
            val setMS = set.seconds
            val interval = 10.milliseconds
            var setTimePassed: Duration = resumeAt

            // Setting it to zero because the resume time should only be called on the first run
            resumeAt = Duration.ZERO

            do {
                if (pauseExecution) {
                    return@launch
                }

                if (setTimePassed + interval > setMS) {
                    break
                }

                delay(interval)
                yield()

                setTimePassed += interval
                val progress = Progress.with(setTimePassed, setMS)

                totalTimePassed += interval
                totalTimeRemaining -= interval

                val setData = TimedItemExecutionData(
                    setDuration = set.seconds,
                    setTimePassed = setTimePassed,
                    totalTimePassed = totalTimePassed,
                    totalTimeRemaining = totalTimeRemaining,
                    totalDuration = totalTime,
                )

                val totalProgress = Progress.with(totalTimePassed, totalTime)

                _state.value = ItemExecutionState.SetRunning(
                    item = item,
                    set = set,
                    progress = progress,
                    totalProgress = totalProgress,
                    setData = setData,
                )

                if (pauseExecution) {
                    return@launch
                }
            } while (setTimePassed <= setMS)

            val setTimeRemaining = setMS - (setTimePassed + interval)

            if (setTimeRemaining > Duration.ZERO) {
                delay(setTimeRemaining)
            }

            _state.value = ItemExecutionState.SetFinished(
                item,
                set,
                Progress.COMPLETE,
                totalProgress = Progress.with(totalTimePassed, totalTime),
                setData = TimedItemExecutionData(
                    setDuration = set.seconds,
                    setTimePassed = set.seconds,
                    totalTimePassed = totalTimePassed,
                    totalTimeRemaining = totalTimeRemaining,
                    totalDuration = totalTime,
                ),
            )
            yield()

            if (currSetIndex + 1 < sets.size) {
                currSetIndex += 1
            } else {
                break
            }
        }

        _state.value = ItemExecutionState.Finished(item, true)
    }

    private fun invalidateJob() {
        currJob?.cancel()
        currJob = null
    }
}
