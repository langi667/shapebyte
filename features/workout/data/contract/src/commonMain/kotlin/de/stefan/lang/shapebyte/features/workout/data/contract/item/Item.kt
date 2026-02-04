package de.stefan.lang.shapebyte.features.workout.data.contract.item

import de.stefan.lang.foundation.core.contract.assets.ImageAsset

/**
Represents any kind of workout item, such as exercise, breaks, countdown, cooldown, warm up ...
 */

public interface Item {
    public val name: String
}

public interface ImageContaining {
    public val imageAsset: ImageAsset?
}

public object None : Item {
    override val name: String = "None"
}
