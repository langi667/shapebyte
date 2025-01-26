package de.stefan.lang.shapebyte.features.workout.core.data

import de.stefan.lang.shapebyte.utils.image.data.Image

data class Workout(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val image: Image,
    val type: WorkoutType,
)
