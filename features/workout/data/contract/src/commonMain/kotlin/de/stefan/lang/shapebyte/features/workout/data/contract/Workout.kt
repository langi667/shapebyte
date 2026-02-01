package de.stefan.lang.shapebyte.features.workout.data.contract

import de.stefan.lang.foundation.core.contract.image.ImageResource

data class Workout(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val image: ImageResource,
    val type: WorkoutType,
)
