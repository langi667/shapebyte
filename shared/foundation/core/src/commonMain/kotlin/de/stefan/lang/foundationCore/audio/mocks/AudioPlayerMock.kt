package de.stefan.lang.foundationCore.audio.mocks

import de.stefan.lang.foundationCore.audio.AudioPlaying
import de.stefan.lang.foundationCore.audio.AudioResource

class AudioPlayerMock : AudioPlaying {
    override fun play(file: AudioResource) {
        println("AudioPlayerMock: play ${file.name}")
    }
}
