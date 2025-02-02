package de.stefan.lang.core.assets

import de.stefan.lang.core.image.Image

data class ImageAsset(override val assetName: String) : Asset, Image {
    override val subPath: String = "images/$assetName"
}
