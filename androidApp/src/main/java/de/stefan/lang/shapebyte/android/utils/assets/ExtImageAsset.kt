package de.stefan.lang.shapebyte.android.utils.assets

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import de.stefan.lang.shapebyte.utils.assets.ImageAsset

val ImageAsset.assetsPath: String
    get() = "file:///android_asset/$subPath"