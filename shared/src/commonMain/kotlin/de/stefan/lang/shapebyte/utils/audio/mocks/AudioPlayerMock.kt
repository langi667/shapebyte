package de.stefan.lang.shapebyte.utils.audio.mocks

import de.stefan.lang.shapebyte.utils.audio.AudioPlaying
import de.stefan.lang.shapebyte.utils.audio.AudioResource

class AudioPlayerMock : AudioPlaying {
    override fun play(file: AudioResource) {
        println("AudioPlayerMock: play ${file.name}")
    }
}
