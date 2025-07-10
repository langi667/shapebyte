package de.stefan.lang.foundationCore.api.assets

import de.stefan.lang.foundationCore.api.image.Image

data class ImageAsset(override val assetName: String) : Asset, Image {
    override val subPath: String = "images/$assetName"
}
