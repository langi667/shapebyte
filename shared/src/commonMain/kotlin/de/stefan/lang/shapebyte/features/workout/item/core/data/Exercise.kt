package de.stefan.lang.shapebyte.features.workout.item.core.data

import de.stefan.lang.shapebyte.utils.assets.ImageAsset

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
