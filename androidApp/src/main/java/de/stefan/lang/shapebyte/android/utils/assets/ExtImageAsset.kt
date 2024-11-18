package de.stefan.lang.shapebyte.android.utils.assets

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import de.stefan.lang.shapebyte.utils.assets.ImageAsset

fun ImageAsset.load(
    context: Context,
): Bitmap? {
    return ImageAssetLoader.loadImage(this, context)
}

fun ImageAsset.loadImage(context: Context): ImageBitmap? = this.load(context)?.asImageBitmap()
