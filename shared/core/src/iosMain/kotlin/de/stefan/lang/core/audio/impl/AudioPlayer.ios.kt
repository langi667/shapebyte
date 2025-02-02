package de.stefan.lang.core.audio.impl

import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.resources.impl.AppResourceProvider
import de.stefan.lang.core.audio.AudioPlaying
import de.stefan.lang.core.audio.AudioResource
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual class AudioPlayer actual constructor(
    private val appContextProvider: AppContextProvider,
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