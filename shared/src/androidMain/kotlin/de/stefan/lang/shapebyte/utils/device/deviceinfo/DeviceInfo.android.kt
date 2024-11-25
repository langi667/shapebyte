package de.stefan.lang.shapebyte.utils.device.deviceinfo

import android.content.res.Resources
import android.util.DisplayMetrics
import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.device.safearea.SafeArea
import de.stefan.lang.shapebyte.utils.device.safearea.SafeAreaDetector
import de.stefan.lang.shapebyte.utils.os.OperatingSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class DeviceInfo actual constructor(private val safeAreaDetector: SafeAreaDetector) : DeviceInfoProviding {
    actual override val screenSize: Size by lazy { computeScreenSize() }
    actual override val operatingSystem: OperatingSystem = OperatingSystem.Android
    actual override val safeArea: SafeArea get() = _safeArea

    private var _safeArea: SafeArea = SafeArea()

    private fun computeScreenSize(): Size {
        val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val screenHeightDp = displayMetrics.heightPixels / displayMetrics.density

        val size = Size(screenWidthDp, screenHeightDp)
        return size
    }

    actual override fun readDeviceInfos(): Flow<DeviceInfoProviding> = flow {
        safeAreaDetector.detectSafeArea().collect {
            _safeArea = it

            emit(this@DeviceInfo)
        }
    }
}
