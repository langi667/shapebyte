package de.stefan.lang.shapebyte.utils.assets

import de.stefan.lang.shapebyte.utils.image.Image

data class ImageAsset(override val assetName: String) : Asset, Image {
    override val subPath: String = "images/$assetName"
}
