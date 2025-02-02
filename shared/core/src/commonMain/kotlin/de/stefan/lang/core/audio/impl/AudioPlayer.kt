package de.stefan.lang.core.audio.impl

import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.resources.impl.AppResourceProvider
import de.stefan.lang.core.audio.AudioPlaying
import de.stefan.lang.core.audio.AudioResource
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging

expect class AudioPlayer(
    appContextProvider: AppContextProvider,
    appResourceProvider: AppResourceProvider,
    logger: Logging,
) : AudioPlaying, Loggable {
    override val logger: Logging
    override fun play(file: AudioResource)
}
