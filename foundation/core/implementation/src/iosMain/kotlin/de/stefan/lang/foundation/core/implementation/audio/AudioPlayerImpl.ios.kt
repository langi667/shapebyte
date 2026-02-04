package de.stefan.lang.foundation.core.implementation.audio

import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.audio.AudioPlayer
import de.stefan.lang.foundation.core.contract.audio.AudioResource
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

public actual class AudioPlayerImpl actual constructor(
    private val appContextProvider: ContextProvider,
    private val appResourceProvider: AppResourceProvider,
    public actual override val logger: Logger,
) : AudioPlayer, Loggable {
    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    public actual override fun play(file: AudioResource) {
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
