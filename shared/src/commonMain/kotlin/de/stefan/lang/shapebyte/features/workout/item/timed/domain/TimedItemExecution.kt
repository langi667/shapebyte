package de.stefan.lang.shapebyte.features.workout.item.timed.domain

import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.core.data.None
import de.stefan.lang.shapebyte.features.workout.item.core.data.sumSeconds
import de.stefan.lang.shapebyte.features.workout.item.core.data.sumSecondsTo
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.item.core.domain.TimedItemExecuting
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

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

    private val _state: MutableStateFlow<ItemExecutionState<TimedItemExecutionData>> = MutableStateFlow(
        ItemExecutionState.Idle,
    )

    override val state: StateFlow<ItemExecutionState<TimedItemExecutionData>> get() = _state

    override fun start(scope: CoroutineScope): Boolean {
        if (isRunning) {
            logE("Cannot start a new set while another one is running")
            return false
        }

        _state.value = ItemExecutionState.Started(item)
        startSets(scope)
        return true
    }

    private fun startSets(scope: CoroutineScope) = scope.launch {
        val totalTime = sets.sumSeconds()
        _state.value = ItemExecutionState.Started(item)

        logI("Starting timed item execution for ${item.name} with ${sets.size} sets and a total duration of $totalTime")

        sets.forEachIndexed { index, set ->
            var timePassed: Duration = if (index == 0) { Duration.ZERO } else { sets.sumSecondsTo(index) }
            var timeRemaining = totalTime - timePassed

            yield()
            _state.value = ItemExecutionState.SetStarted(
                item = item,
                set = set,
                progress = Progress.ZERO,
                totalProgress = Progress(index.toFloat() / sets.size.toFloat()),
                setData = TimedItemExecutionData(
                    setDuration = set.seconds,
                    setTimePassed = 0.seconds,
                    totalTimePassed = timePassed,
                    totalTimeRemaining = timeRemaining,
                    totalDuration = totalTime,
                ),
            )

            delay(set.seconds)

            timePassed += set.seconds
            timeRemaining -= set.seconds

            _state.value = ItemExecutionState.SetFinished(
                item,
                set,
                Progress.COMPLETE,
                totalProgress = Progress((index + 1).toFloat() / sets.size.toFloat()),
                setData = TimedItemExecutionData(
                    setDuration = set.seconds,
                    setTimePassed = set.seconds,
                    totalTimePassed = timePassed,
                    totalTimeRemaining = timeRemaining,
                    totalDuration = totalTime,
                ),
            )

            yield()
        }

        _state.value = ItemExecutionState.Finished(item)
    }
}
