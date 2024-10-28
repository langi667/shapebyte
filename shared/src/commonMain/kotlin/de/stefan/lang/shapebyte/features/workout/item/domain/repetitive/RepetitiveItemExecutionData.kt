package de.stefan.lang.shapebyte.features.workout.item.domain.repetitive

data class RepetitiveItemExecutionData(
    val repsPerSetPerformed: UInt,
    val totalRepsPerformed: UInt,
    val totalRepsRemaining: UInt? = null,
    val totalRepsGoal: UInt? = null,
)
