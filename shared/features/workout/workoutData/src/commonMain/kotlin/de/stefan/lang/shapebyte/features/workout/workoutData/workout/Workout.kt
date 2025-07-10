package de.stefan.lang.shapebyte.features.workout.workoutData.workout

import de.stefan.lang.foundationCore.api.image.ImageResource
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutType

data class Workout(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val image: ImageResource,
    val type: WorkoutType,
)
