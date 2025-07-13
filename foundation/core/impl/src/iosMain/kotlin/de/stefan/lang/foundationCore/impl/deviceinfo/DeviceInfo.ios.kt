package de.stefan.lang.foundationCore.impl.deviceinfo

import de.stefan.lang.foundationCore.api.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.impl.safearea.SafeAreaDetector
import de.stefan.lang.foundationCore.api.os.OperatingSystem
import de.stefan.lang.foundationCore.api.devicesize.Size
import de.stefan.lang.foundationCore.api.safearea.SafeArea
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.UIKit.UIScreen

actual class DeviceInfo actual constructor(private val safeAreaDetector: SafeAreaDetector) :
    DeviceInfoProviding {
    actual override val screenSize: Size by lazy { computeScreenSize() }
    actual override val operatingSystem: OperatingSystem = OperatingSystem.IOS
    actual override val safeArea: SafeArea get() = _safeArea

    private var _safeArea: SafeArea = SafeArea()

    @OptIn(ExperimentalForeignApi::class)
    private fun computeScreenSize(): Size {
        val size: Size = UIScreen.mainScreen.bounds().useContents {
            val height = size.height.toFloat()
            val width = size.width.toFloat()

            Size(width, height)
        }

        return size
    }

    actual override fun readDeviceInfos(): Flow<DeviceInfoProviding> = flow {
        safeAreaDetector.detectSafeArea().collect {
            _safeArea = it

            emit(this@DeviceInfo)
        }
    }
}