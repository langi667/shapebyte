package de.stefan.lang.shapebyte.features.workout.item.domain.timed

import kotlin.time.Duration

data class TimedItemExecutionData(
    val timePassed: Duration,
    val timeRemaining: Duration,
    val totalDuration: Duration,
)
