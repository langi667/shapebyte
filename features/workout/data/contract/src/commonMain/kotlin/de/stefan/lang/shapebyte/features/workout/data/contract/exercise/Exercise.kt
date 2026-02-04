package de.stefan.lang.shapebyte.features.workout.data.contract.exercise

import de.stefan.lang.foundation.core.contract.assets.ImageAsset
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ImageContaining
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item

public data class Exercise public constructor(
    override val name: String,
    public val assetName: String? = null,
) : Item, ImageContaining {
    public companion object {
        public val None: Exercise = Exercise("")
    }

    public override val imageAsset: ImageAsset?
        get() {
            val retVal: ImageAsset? = if (assetName != null) {
                ImageAsset(assetName)
            } else {
                null
            }
            return retVal
        }
}
