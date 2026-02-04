package de.stefan.lang.shapebyte.features.workout.domain.contract.item

public data class RepetitiveItemExecutionData(
    public val repsPerSetPerformed: UInt,
    public val totalRepsPerformed: UInt,
    public val totalRepsRemaining: UInt? = null,
    public val totalRepsGoal: UInt? = null,
)
