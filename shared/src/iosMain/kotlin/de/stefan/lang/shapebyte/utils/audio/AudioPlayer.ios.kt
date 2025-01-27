package de.stefan.lang.shapebyte.utils.audio

import de.stefan.lang.shapebyte.utils.app.appcontext.AppContextProvider
import de.stefan.lang.shapebyte.utils.app.appresources.AppResourceProvider
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual class AudioPlayer actual constructor(
    val file: AudioResource,
    private val appContextProvider: AppContextProvider,
    private val appResourceProvider: AppResourceProvider,
    actual override val logger: Logging,
): Loggable{
    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun play() {
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