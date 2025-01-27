package de.stefan.lang.shapebyte.utils.audio

import android.content.Context
import android.media.MediaPlayer
import de.stefan.lang.shapebyte.utils.app.appcontext.AppContextProvider
import de.stefan.lang.shapebyte.utils.app.appresources.AppResourceProvider
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import de.stefan.lang.shapebyte.utils.resources.audio.AudioMapping

actual class AudioPlayer actual constructor(
    val file: AudioResourceFile,
    private val appContextProvider: AppContextProvider,
    private val appResourceProvider: AppResourceProvider,
    override val logger: Logging,
) : Loggable {
    private val context: Context = appContextProvider.androidContext
    private val audioMapping: AudioMapping? = appResourceProvider.audioMapping

    actual fun play() {
        val audioMapping = audioMapping ?: kotlin.run {
            logE("audioMapping is null, Check if it is provided during app setup?")
            return
        }

        val resId = audioMapping.resIdFor(file)
        val mediaPlayer = MediaPlayer.create(context, resId)

        mediaPlayer.start()
    }
}
