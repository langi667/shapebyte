package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.copy
import platform.UIKit.UIScreen

actual class DeviceInfo: DeviceInfoProviding {
    actual override val screenSize: Size by lazy { computeScreenSize() }

    @OptIn(ExperimentalForeignApi::class)
    private fun computeScreenSize(): Size {

        var width: Float = 0f
        var height: Float = 0f

        UIScreen.mainScreen.bounds().copy {
            height = size.height.toFloat()
            width = size.width.toFloat()

        }

        val size = Size(width, height)
        return size
    }
}