package de.stefan.lang.foundationCore.impl.audio

import android.content.Context
import android.media.MediaPlayer
import de.stefan.lang.coreutils.api.logging.Loggable
import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.audio.AudioPlaying
import de.stefan.lang.foundationCore.api.audio.AudioResource
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider
import de.stefan.lang.foundationCore.api.resources.ResourceFileMapping

actual class AudioPlayer actual constructor(
    private val appContextProvider: ContextProvider,
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
