package de.stefan.lang.foundationCore.impl.audio

import de.stefan.lang.coreutils.contract.logging.Loggable
import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.audio.AudioPlaying
import de.stefan.lang.foundation.core.contract.audio.AudioResource
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual class AudioPlayer actual constructor(
    private val appContextProvider: ContextProvider,
    private val appResourceProvider: AppResourceProvider,
    actual override val logger: Logging,
): AudioPlaying, Loggable {
    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    actual override fun play(file: AudioResource) {
        logD("Playing audio file: ${file.id}")

        appResourceProvider.mainBundle.pathForResource(file.fileName, file.fileEnding)?.let { path ->
            logD("File found in bundle audio file: ${file.id}")
            audioPlayer = AVAudioPlayer(NSURL.fileURLWithPath(path), null, null)
            audioPlayer?.play()
        } ?: run {
            logE("File not found in bundle audio file: ${file.id}")
        }
    }
}