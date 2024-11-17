package de.stefan.lang.shapebyte.android.utils.assets

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import de.stefan.lang.shapebyte.utils.image.load

fun ImageAsset.loadImage(context: Context): ImageBitmap? = this.load(context)?.asImageBitmap()
