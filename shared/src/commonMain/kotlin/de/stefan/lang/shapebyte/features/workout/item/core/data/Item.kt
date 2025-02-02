package de.stefan.lang.shapebyte.features.workout.item.core.data

import de.stefan.lang.core.assets.ImageAsset

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
