package de.stefan.lang.foundation.core.implementation.assets

import de.stefan.lang.coreutils.contract.logging.Loggable
import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoading

expect class FileAssetLoader(
    appContextProvider: ContextProvider,
    logging: Logging,
) :
    FileAssetLoading,
    Loggable {
    override val logger: Logging

    override fun loadFile(fileAsset: FileAsset): String
}
