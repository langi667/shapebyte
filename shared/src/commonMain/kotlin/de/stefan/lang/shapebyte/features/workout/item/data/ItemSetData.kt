package de.stefan.lang.shapebyte.features.workout.item.data

import de.stefan.lang.shapebyte.utils.Progress
import kotlin.time.Duration

// TODO: consider adding referring ItemSet
sealed interface ItemSetData {
    data class Timed(
        val timePassed: Duration,
        val timeRemaining: Duration,
        override val progress: Progress,
        val nextProgress: Progress,
    ) : ItemSetData

    data class Repetitions(
        val repetitionsDone: UInt,
        val repetitionGoal: UInt? = null,
        override val progress: Progress,
    ) : ItemSetData

    val progress: Progress
}
