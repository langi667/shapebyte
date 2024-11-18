package de.stefan.lang.shapebyte.utils.assets.impl

import android.content.Context
import de.stefan.lang.shapebyte.utils.assets.FileAsset
import de.stefan.lang.shapebyte.utils.assets.FileAssetLoading
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import java.io.IOException

actual class FileAssetLoader actual constructor(logging: Logging) :
    FileAssetLoading,
    Loggable {
    actual override val logger = logging
    private lateinit var context: Context

    actual override fun setup(context: Any?) {
        this.context = context as Context
    }

    actual override fun loadFile(fileAsset: FileAsset): String {
        val retVal: String = try {
            context.assets.open(fileAsset.subPath).bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            logE("Error loading file: ${fileAsset.subPath} with error: $ex")
            ""
        }
        return retVal
    }
}
