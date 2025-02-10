package de.stefan.lang.foundationCore.assets

import de.stefan.lang.foundationCore.image.Image

data class ImageAsset(override val assetName: String) : Asset, Image {
    override val subPath: String = "images/$assetName"
}
