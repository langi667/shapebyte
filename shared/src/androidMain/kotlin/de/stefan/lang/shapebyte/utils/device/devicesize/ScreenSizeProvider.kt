package de.stefan.lang.shapebyte.utils.device.devicesize

import android.content.res.Resources
import android.util.DisplayMetrics

actual class ScreenSizeProvider : ScreenSizeProviding {
    actual override val screenSize: Size by lazy { computeScreenSize() }

    private fun computeScreenSize(): Size {
        val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val screenHeightDp = displayMetrics.heightPixels / displayMetrics.density

        val size = Size(screenWidthDp, screenHeightDp)
        return size
    }
}
