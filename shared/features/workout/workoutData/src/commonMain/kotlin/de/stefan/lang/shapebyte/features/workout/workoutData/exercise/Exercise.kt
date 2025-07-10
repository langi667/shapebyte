package de.stefan.lang.shapebyte.features.workout.workoutData.exercise

import de.stefan.lang.foundationCore.api.assets.ImageAsset
import de.stefan.lang.shapebyte.features.workout.workoutData.item.ImageContaining
import de.stefan.lang.shapebyte.features.workout.workoutData.item.Item

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
