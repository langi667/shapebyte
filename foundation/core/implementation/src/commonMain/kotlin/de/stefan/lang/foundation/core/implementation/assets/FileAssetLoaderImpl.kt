package de.stefan.lang.foundation.core.implementation.assets

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

public expect class FileAssetLoaderImpl public constructor(
    appContextProvider: ContextProvider,
    logging: Logger,
) :
    FileAssetLoader,
    Loggable {
    public override val logger: Logger

    public override fun loadFile(fileAsset: FileAsset): String
}
