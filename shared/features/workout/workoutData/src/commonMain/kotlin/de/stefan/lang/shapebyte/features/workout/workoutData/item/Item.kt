package de.stefan.lang.shapebyte.features.workout.workoutData.item

import de.stefan.lang.foundationCore.api.assets.ImageAsset

/**
Represents any kind of workout item, such as exercise, breaks, countdown, cooldown, warm up ...
 */

interface Item {
    val name: String
}

interface ImageContaining {
    val imageAsset: ImageAsset?
}

object None : Item {
    override val name: String = "None"
}
