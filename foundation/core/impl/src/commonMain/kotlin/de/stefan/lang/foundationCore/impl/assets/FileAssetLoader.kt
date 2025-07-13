package de.stefan.lang.foundationCore.impl.assets

import de.stefan.lang.coreutils.api.logging.Loggable
import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.assets.FileAsset
import de.stefan.lang.foundationCore.api.assets.FileAssetLoading

expect class FileAssetLoader(
    appContextProvider: ContextProvider,
    logging: Logging,
) :
    FileAssetLoading,
    Loggable {
    override val logger: Logging

    override fun loadFile(fileAsset: FileAsset): String
}
