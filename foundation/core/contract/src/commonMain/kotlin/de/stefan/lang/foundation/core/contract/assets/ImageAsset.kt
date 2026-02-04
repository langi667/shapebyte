package de.stefan.lang.foundation.core.contract.assets

import de.stefan.lang.foundation.core.contract.image.Image

public data class ImageAsset public constructor(override val assetName: String) : Asset, Image {
    override val subPath: String = "images/$assetName"
}
