package de.stefan.lang.shapebyte.android.shared.resources.mapping.image

import de.stefan.lang.shapebyte.utils.image.data.ImageResource

interface ImageMapping {
    fun resIdFor(image: ImageResource): Int
}
