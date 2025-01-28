package de.stefan.lang.shapebyte.utils.audio.impl

import android.content.Context
import android.media.MediaPlayer
import de.stefan.lang.shapebyte.utils.app.appcontext.AppContextProvider
import de.stefan.lang.shapebyte.utils.app.appresources.AppResourceProvider
import de.stefan.lang.shapebyte.utils.audio.AudioPlaying
import de.stefan.lang.shapebyte.utils.audio.AudioResource
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import de.stefan.lang.shapebyte.utils.resources.ResourceFileMapping

actual class AudioPlayer actual constructor(
    private val appContextProvider: AppContextProvider,
    private val appResourceProvider: AppResourceProvider,
    actual override val logger: Logging,
) : AudioPlaying, Loggable {
    private val context: Context = appContextProvider.androidContext
    private val audioMapping: ResourceFileMapping? = appResourceProvider.audioMapping

    actual override fun play(file: AudioResource) {
        val audioMapping = audioMapping ?: kotlin.run {
            logE("audioMapping is null, Check if it is provided during app setup?")
            return
        }

        val resId = audioMapping.resIdFor(file)
        val mediaPlayer = MediaPlayer.create(context, resId)

        mediaPlayer.start()
    }
}
