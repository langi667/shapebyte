package de.stefan.lang.shapebyte.android.utils.assets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import java.io.IOException

/**
 * Loads an image asset from the assets folder.
 *
 * Must be a singleton to have images rendered in Composable Previews
 */

// TODO: Test
object ImageAssetLoader {
    fun loadImage(asset: ImageAsset, context: Context): Bitmap? {
        return try {
            val assetManager = context.assets
            BitmapFactory.decodeStream(assetManager.open("images/${asset.assetName}"))
        } catch (_: IOException) {
            null
        }
    }
}
