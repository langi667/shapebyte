package de.stefan.lang.shapebyte.android.shared.image.ui

import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.utils.image.data.ImageResource

// TODO: generate automatically
// TODO: Test
// TODO: add fallback image to iOS
// TODO: use DPI
object ImageMapper {
    val fallbackImage = R.drawable.fallback_img

    private val mapping = mapOf(
        "sprints" to R.drawable.sprints,
        // TODO: add more images
    )

    fun resIdFor(image: ImageResource): Int {
        return mapping[image.id] ?: fallbackImage
    }
}
