package de.stefan.lang.shapebyte.features.workout.data.contract.exercise

import de.stefan.lang.foundation.core.contract.assets.ImageAsset
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ImageContaining
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item

data class Exercise(
    override val name: String,
    val assetName: String? = null,
) : Item, ImageContaining {
    companion object {
        val None = Exercise("")
    }

    override val imageAsset: ImageAsset?
        get() {
            val retVal: ImageAsset? = if (assetName != null) {
                ImageAsset(assetName)
            } else {
                null
            }
            return retVal
        }
}
