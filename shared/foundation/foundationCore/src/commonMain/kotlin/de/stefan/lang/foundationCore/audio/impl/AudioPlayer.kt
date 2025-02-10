package de.stefan.lang.foundationCore.audio.impl

import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.audio.AudioPlaying
import de.stefan.lang.foundationCore.audio.AudioResource
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider

expect class AudioPlayer(
    appContextProvider: ContextProvider,
    appResourceProvider: AppResourceProvider,
    logger: Logging,
) : AudioPlaying, Loggable {
    override val logger: Logging
    override fun play(file: AudioResource)
}
