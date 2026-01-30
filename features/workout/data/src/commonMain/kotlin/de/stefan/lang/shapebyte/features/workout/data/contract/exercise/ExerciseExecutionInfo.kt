package de.stefan.lang.shapebyte.features.workout.data.contract.exercise

import de.stefan.lang.shapebyte.features.workout.data.contract.item.ImageContaining
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item

enum class IntervalExerciseInfo {
    HIGH,
    LOW,
    NONE,
}

/**
 * Contains infos of exercises that relate to its specific execution
 * Like if it is the high or low interval exercise in a Timed / HIIT set
 */

data class ExerciseExecutionInfo(
    val exercise: Exercise,
    val intervalExerciseInfo: IntervalExerciseInfo = IntervalExerciseInfo.NONE,
) : Item,
    ImageContaining by exercise {
    override val name: String
        get() = exercise.name
}
