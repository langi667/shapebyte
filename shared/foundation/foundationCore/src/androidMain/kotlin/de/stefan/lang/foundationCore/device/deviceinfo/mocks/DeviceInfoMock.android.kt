package de.stefan.lang.foundationCore.device.deviceinfo.mocks
import de.stefan.lang.foundationCore.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.device.devicesize.Size
import de.stefan.lang.foundationCore.device.safearea.SafeArea
import de.stefan.lang.foundationCore.os.OperatingSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class DeviceInfoMock actual constructor() : DeviceInfoProviding {
    actual override fun readDeviceInfos(): Flow<DeviceInfoProviding> = flowOf(this)
    actual override val screenSize: Size = Size(width = 393.0f, height = 852.0f)
    actual override val operatingSystem: OperatingSystem = OperatingSystem.Android
    actual override val safeArea: SafeArea = SafeArea(
        top = 0.0,
        leading = 0.0,
        bottom = 0.0,
        trailing = 0.0,
    )
}
