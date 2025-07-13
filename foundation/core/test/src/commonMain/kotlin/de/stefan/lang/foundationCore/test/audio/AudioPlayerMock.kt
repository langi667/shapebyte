package de.stefan.lang.foundationCore.test.audio

import de.stefan.lang.foundationCore.api.audio.AudioPlaying
import de.stefan.lang.foundationCore.api.audio.AudioResource

class AudioPlayerMock : AudioPlaying {
    override fun play(file: AudioResource) {
        println("AudioPlayerMock: play ${file.name}")
    }
}
