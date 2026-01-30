package de.stefan.lang.shapebyte.features.workout.data.contract.item

import de.stefan.lang.foundation.core.contract.assets.ImageAsset

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
