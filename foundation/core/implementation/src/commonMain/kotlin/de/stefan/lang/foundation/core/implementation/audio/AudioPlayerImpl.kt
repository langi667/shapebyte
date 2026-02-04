package de.stefan.lang.foundation.core.implementation.audio

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.audio.AudioPlayer
import de.stefan.lang.foundation.core.contract.audio.AudioResource
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

public expect class AudioPlayerImpl public constructor(
    appContextProvider: ContextProvider,
    appResourceProvider: AppResourceProvider,
    logger: Logger,
) : AudioPlayer, Loggable {
    public override val logger: Logger
    public override fun play(file: AudioResource)
}
