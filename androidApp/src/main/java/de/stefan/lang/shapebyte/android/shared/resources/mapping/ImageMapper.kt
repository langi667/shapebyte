package de.stefan.lang.shapebyte.android.shared.resources.mapping

import de.stefan.lang.foundationCore.resources.ResourceFile
import de.stefan.lang.foundationCore.resources.ResourceFileMapping
import de.stefan.lang.shapebyte.android.R

// TODO: generate automatically
// TODO: Test
// TODO: add fallback image to iOS
// TODO: use DPI

object ImageMapper : ResourceFileMapping {
    private val fallbackImage = R.drawable.fallback_img

    private val mapping = mapOf(
        "sprints.png" to R.drawable.sprints,
        // TODO: add more images
    )

    override fun resIdFor(file: ResourceFile): Int {
        return mapping[file.id] ?: fallbackImage
    }
}
