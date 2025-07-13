package de.stefan.lang.shapebyte.features.workout.api

import de.stefan.lang.foundationCore.api.image.ImageResource

data class Workout(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val image: ImageResource,
    val type: WorkoutType,
)
