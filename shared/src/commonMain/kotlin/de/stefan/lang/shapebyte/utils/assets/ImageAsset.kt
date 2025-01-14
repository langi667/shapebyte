package de.stefan.lang.shapebyte.utils.assets

import de.stefan.lang.shapebyte.utils.image.data.Image

data class ImageAsset(override val assetName: String) : Asset, Image {
    override val id: String = assetName
    override val subPath: String = "images/$assetName"
}
