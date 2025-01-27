package de.stefan.lang.shapebyte.android.utils.audio

import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.utils.audio.AudioResourceFile
import de.stefan.lang.shapebyte.utils.resources.audio.AudioMapping

// TODO: should be auto generated
object AudioMapper : AudioMapping {
    private val fallbackAudio = R.raw.ding

    private val mapping = mapOf(
        "ding" to R.raw.ding,
        // TODO: add more audio files
    )

    override fun resIdFor(audio: AudioResourceFile): Int {
        return mapping[audio.id] ?: fallbackAudio
    }
}
