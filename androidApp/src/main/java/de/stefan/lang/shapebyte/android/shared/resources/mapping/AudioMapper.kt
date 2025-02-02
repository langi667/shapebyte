package de.stefan.lang.shapebyte.android.shared.resources.mapping

import de.stefan.lang.core.resources.ResourceFile
import de.stefan.lang.core.resources.ResourceFileMapping
import de.stefan.lang.shapebyte.android.R

// TODO: should be auto generated
object AudioMapper : ResourceFileMapping {
    private val fallbackAudio = R.raw.ding

    private val mapping = mapOf(
        "ding.mp3" to R.raw.ding,
        // TODO: add more audio files
    )

    override fun resIdFor(file: ResourceFile): Int {
        return mapping[file.id] ?: fallbackAudio
    }
}
