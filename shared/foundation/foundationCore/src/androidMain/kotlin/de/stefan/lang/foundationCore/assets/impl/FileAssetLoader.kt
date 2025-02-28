package de.stefan.lang.foundationCore.assets.impl

import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.assets.FileAsset
import de.stefan.lang.foundationCore.assets.FileAssetLoading
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
