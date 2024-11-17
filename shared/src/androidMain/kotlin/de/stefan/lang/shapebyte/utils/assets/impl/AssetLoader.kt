package de.stefan.lang.shapebyte.utils.assets.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import de.stefan.lang.shapebyte.utils.assets.AssetLoading
import de.stefan.lang.shapebyte.utils.assets.FileAsset
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import java.io.IOException

interface AssetLoaderAndroid {
    fun loadImageAsset(asset: ImageAsset, context: Context): Bitmap? {
        return try {
            val assetManager = context.assets
            BitmapFactory.decodeStream(assetManager.open("images/${asset.assetName}"))
        } catch (_: IOException) {
            null
        }
    }
}

actual class AssetLoader actual constructor(logging: Logging) :
    AssetLoading,
    AssetLoaderAndroid,
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
