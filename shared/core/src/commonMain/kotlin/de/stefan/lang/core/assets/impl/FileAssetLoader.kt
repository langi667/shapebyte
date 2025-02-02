package de.stefan.lang.core.assets.impl

import de.stefan.lang.core.assets.FileAsset
import de.stefan.lang.core.assets.FileAssetLoading
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import de.stefan.lang.core.app.AppContextProvider

expect class FileAssetLoader(
    appContextProvider: AppContextProvider,
    logging: Logging
) :
    FileAssetLoading,
    Loggable {
    override val logger: Logging

    override fun loadFile(fileAsset: FileAsset): String
}
