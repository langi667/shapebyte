package de.stefan.lang.shapebyte.utils.device.deviceinfo

import android.content.res.Resources
import android.util.DisplayMetrics
import de.stefan.lang.shapebyte.utils.device.devicesize.Size

actual class DeviceInfo : DeviceInfoProviding {
    actual override val screenSize: Size by lazy { computeScreenSize() }

    private fun computeScreenSize(): Size {
        val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val screenHeightDp = displayMetrics.heightPixels / displayMetrics.density

        val size = Size(screenWidthDp, screenHeightDp)
        return size
    }
}
