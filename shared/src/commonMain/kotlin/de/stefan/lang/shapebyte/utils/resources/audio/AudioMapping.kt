package de.stefan.lang.shapebyte.utils.resources.audio

import de.stefan.lang.shapebyte.utils.audio.AudioResourceFile

interface AudioMapping {
    fun resIdFor(file: AudioResourceFile): Int
}
