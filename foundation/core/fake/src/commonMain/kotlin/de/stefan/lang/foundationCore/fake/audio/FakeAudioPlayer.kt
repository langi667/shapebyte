package de.stefan.lang.foundationCore.fake.audio

import de.stefan.lang.foundationCore.api.audio.AudioPlaying
import de.stefan.lang.foundationCore.api.audio.AudioResource

class FakeAudioPlayer : AudioPlaying {
    override fun play(file: AudioResource) {
        println("FakeAudioPlayer: play ${file.name}")
    }
}
