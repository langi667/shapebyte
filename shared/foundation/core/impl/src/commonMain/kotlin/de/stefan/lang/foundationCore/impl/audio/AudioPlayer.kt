package de.stefan.lang.foundationCore.impl.audio

import de.stefan.lang.coreutils.api.Loggable
import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.audio.AudioPlaying
import de.stefan.lang.foundationCore.api.audio.AudioResource
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider

expect class AudioPlayer(
    appContextProvider: ContextProvider,
    appResourceProvider: AppResourceProvider,
    logger: Logging,
) : AudioPlaying, Loggable {
    override val logger: Logging
    override fun play(file: AudioResource)
}
