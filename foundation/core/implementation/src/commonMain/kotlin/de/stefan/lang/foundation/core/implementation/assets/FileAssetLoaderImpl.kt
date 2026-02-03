package de.stefan.lang.foundation.core.implementation.assets

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

expect class FileAssetLoaderImpl(
    appContextProvider: ContextProvider,
    logging: Logger,
) :
    FileAssetLoader,
    Loggable {
    override val logger: Logger

    override fun loadFile(fileAsset: FileAsset): String
}
