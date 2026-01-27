package de.stefan.lang.foundation.core.implementation.audio

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.audio.AudioPlaying
import de.stefan.lang.foundation.core.contract.audio.AudioResource
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging

expect class AudioPlayer(
    appContextProvider: ContextProvider,
    appResourceProvider: AppResourceProvider,
    logger: Logging,
) : AudioPlaying, Loggable {
    override val logger: Logging
    override fun play(file: AudioResource)
}
