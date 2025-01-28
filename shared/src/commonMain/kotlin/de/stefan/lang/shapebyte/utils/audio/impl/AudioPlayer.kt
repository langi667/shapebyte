package de.stefan.lang.shapebyte.utils.audio.impl

import de.stefan.lang.shapebyte.utils.app.appcontext.AppContextProvider
import de.stefan.lang.shapebyte.utils.app.appresources.AppResourceProvider
import de.stefan.lang.shapebyte.utils.audio.AudioPlaying
import de.stefan.lang.shapebyte.utils.audio.AudioResource
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging

expect class AudioPlayer(
    appContextProvider: AppContextProvider,
    appResourceProvider: AppResourceProvider,
    logger: Logging,
) : AudioPlaying, Loggable {
    override val logger: Logging
    override fun play(file: AudioResource)
}
