package de.stefan.lang.foundationCore.assets.impl

import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.assets.FileAsset
import de.stefan.lang.foundationCore.assets.FileAssetLoading

expect class FileAssetLoader(
    appContextProvider: ContextProvider,
    logging: Logging,
) :
    FileAssetLoading,
    Loggable {
    override val logger: Logging

    override fun loadFile(fileAsset: FileAsset): String
}
