package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import kotlin.time.Duration

/**
 * Represents the data of a timed item execution.
 *
 * For example a timer of 5 seconds is a single item with 5 sets. Each set is 1 sec.
 *
 * @param setDuration Duration of a single set (ex.: 1 sec)
 * @param setTimePassed time that has passed in between start and finish of a single set (ex.500 ms)
 * @param totalTimePassed overall time that has passed (ex. 2.5 seconds => 2 sets are finished 3rd is in progress)
 * @param totalDuration total duration of the item (ex. 5 seconds)
 */
public data class TimedItemExecutionData(
    public val setDuration: Duration,
    public val setTimePassed: Duration,
    public val totalTimePassed: Duration,
    public val totalTimeRemaining: Duration,
    public val totalDuration: Duration,
)
