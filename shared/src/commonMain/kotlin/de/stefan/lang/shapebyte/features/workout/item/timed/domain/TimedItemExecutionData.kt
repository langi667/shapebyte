package de.stefan.lang.shapebyte.features.workout.item.timed.domain

import kotlin.time.Duration

data class TimedItemExecutionData(
    val setDuration: Duration,
    val setTimePassed: Duration, // time in between start and finished of a single set
    val totalTimePassed: Duration,
    val totalTimeRemaining: Duration,
    val totalDuration: Duration,
)
