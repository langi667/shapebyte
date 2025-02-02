package de.stefan.lang.core.audio.mocks

import de.stefan.lang.core.audio.AudioPlaying
import de.stefan.lang.core.audio.AudioResource

class AudioPlayerMock : AudioPlaying {
    override fun play(file: AudioResource) {
        println("AudioPlayerMock: play ${file.name}")
    }
}
