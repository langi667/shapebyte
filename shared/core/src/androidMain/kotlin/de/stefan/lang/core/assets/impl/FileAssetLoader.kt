package de.stefan.lang.core.assets.impl

import de.stefan.lang.core.assets.FileAsset
import de.stefan.lang.core.assets.FileAssetLoading
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import de.stefan.lang.core.app.AppContextProvider
import java.io.IOException

actual class FileAssetLoader actual constructor(
    private val appContextProvider: AppContextProvider,
    logging: Logging
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
