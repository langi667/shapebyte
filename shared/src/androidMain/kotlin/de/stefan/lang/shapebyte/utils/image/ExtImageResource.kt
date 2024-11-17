package de.stefan.lang.shapebyte.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import java.io.IOException

// TODO: use AssetLoader !
fun ImageAsset.load(context: Context): Bitmap? {
    return try {
        val assetManager = context.assets
        BitmapFactory.decodeStream(assetManager.open("images/${this.assetName}"))
    } catch (_: IOException) {
        null
    }
}
