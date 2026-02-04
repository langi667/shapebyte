package de.stefan.lang.foundation.core.implementation.assets

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import java.io.IOException

public actual class FileAssetLoaderImpl actual constructor(
    private val appContextProvider: ContextProvider,
    logging: Logger,
) :
    FileAssetLoader,
    Loggable {

    public actual override val logger: Logger = logging

    public actual override fun loadFile(fileAsset: FileAsset): String {
        val retVal: String = try {
            appContextProvider.androidContext.assets.open(fileAsset.subPath).bufferedReader()
                .use { it.readText() }
        } catch (ex: IOException) {
            logE("Error loading file: ${fileAsset.subPath} with error: $ex")
            ""
        }
        return retVal
    }
}
