package de.stefan.lang.foundation.core.fake.audio

import de.stefan.lang.foundation.core.contract.audio.AudioPlayer
import de.stefan.lang.foundation.core.contract.audio.AudioResource

class AudioPlayerFake : AudioPlayer {
    override fun play(file: AudioResource) {
        println("AudioPlayerFake: play ${file.name}")
    }
}
