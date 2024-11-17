package de.stefan.lang.shapebyte.utils.assets

data class ImageAsset(override val assetName: String) : Asset {
    override val subPath: String = "images/$assetName"
}
