package de.stefan.lang.shapebyte.features.workout.data

import de.stefan.lang.shapebyte.utils.Progress
import kotlin.time.Duration

sealed interface ItemSetData {
    data class Timed(
        val timePassed: Duration,
        val timeRemaining: Duration,
        val progress: Progress,
        val nextProgress: Progress,
    ) : ItemSetData
}
