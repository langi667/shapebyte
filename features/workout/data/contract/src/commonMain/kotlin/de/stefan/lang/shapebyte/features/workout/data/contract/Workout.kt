package de.stefan.lang.shapebyte.features.workout.data.contract

import de.stefan.lang.foundation.core.contract.image.ImageResource

public data class Workout public constructor(
    public val id: Int,
    public val name: String,
    public val shortDescription: String,
    public val image: ImageResource,
    public val type: WorkoutType,
)
