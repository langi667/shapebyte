package de.stefan.lang.foundationCore.impl.audio

import de.stefan.lang.coreutils.api.Loggable
import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.audio.AudioPlaying
import de.stefan.lang.foundationCore.api.audio.AudioResource
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider
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