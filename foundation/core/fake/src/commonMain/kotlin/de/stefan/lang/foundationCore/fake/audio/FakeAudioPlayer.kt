package de.stefan.lang.foundationCore.fake.audio

import de.stefan.lang.foundation.core.contract.audio.AudioPlaying
import de.stefan.lang.foundation.core.contract.audio.AudioResource

class FakeAudioPlayer : AudioPlaying {
    override fun play(file: AudioResource) {
        println("FakeAudioPlayer: play ${file.name}")
    }
}
