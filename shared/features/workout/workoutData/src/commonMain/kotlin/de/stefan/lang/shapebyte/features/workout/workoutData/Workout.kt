package de.stefan.lang.shapebyte.features.workout.workoutData

import de.stefan.lang.foundationCore.image.ImageResource

data class Workout(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val image: ImageResource,
    val type: WorkoutType,
)
