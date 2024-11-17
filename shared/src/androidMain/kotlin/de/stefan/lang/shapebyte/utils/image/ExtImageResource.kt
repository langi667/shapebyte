package de.stefan.lang.shapebyte.utils.image

import android.content.Context
import android.graphics.Bitmap
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import de.stefan.lang.shapebyte.utils.assets.impl.AssetLoaderAndroid
import java.io.IOException

fun ImageAsset.load(
    context: Context,
    assetLoader: AssetLoaderAndroid? = DPI.assetLoader() as? AssetLoaderAndroid,
): Bitmap? {
    return try {
        val assetLoaderNotNull = assetLoader ?: DPI.assetLoader() as AssetLoaderAndroid
        return assetLoaderNotNull.loadImageAsset(this, context)
    } catch (_: IOException) {
        null
    }
}
