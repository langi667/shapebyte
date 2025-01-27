package de.stefan.lang.shapebyte.utils.audio

import de.stefan.lang.shapebyte.utils.app.appcontext.AppContextProvider
import de.stefan.lang.shapebyte.utils.app.appresources.AppResourceProvider
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging

expect class AudioPlayer(
    file: AudioResource,
    appContextProvider: AppContextProvider,
    appResourceProvider: AppResourceProvider,
    logger: Logging,
) : Loggable {
    override val logger: Logging
    fun play()
}
