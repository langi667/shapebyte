package de.stefan.lang.foundation.core.implementation.audio

import android.content.Context
import android.media.MediaPlayer
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.audio.AudioPlayer
import de.stefan.lang.foundation.core.contract.audio.AudioResource
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import de.stefan.lang.foundation.core.contract.resources.ResourceFileMapping
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

actual class AudioPlayerImpl actual constructor(
    private val appContextProvider: ContextProvider,
    private val appResourceProvider: AppResourceProvider,
    actual override val logger: Logger,
) : AudioPlayer, Loggable {
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
