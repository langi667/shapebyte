package de.stefan.lang.foundation.core.implementation.assets

import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoading
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging
import java.io.IOException

actual class FileAssetLoader actual constructor(
    private val appContextProvider: ContextProvider,
    logging: Logging,
) :
    FileAssetLoading,
    Loggable {

    actual override val logger = logging

    actual override fun loadFile(fileAsset: FileAsset): String {
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
