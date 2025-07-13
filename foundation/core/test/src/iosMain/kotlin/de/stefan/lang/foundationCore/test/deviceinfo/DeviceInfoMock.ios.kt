package de.stefan.lang.foundationCore.test.deviceinfo

import de.stefan.lang.foundationCore.api.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.api.os.OperatingSystem
import de.stefan.lang.foundationCore.api.devicesize.Size
import de.stefan.lang.foundationCore.api.safearea.SafeArea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Mock for iOS 16 device info.
 */

// TODO: consider reading from file
actual class DeviceInfoMock actual constructor() : DeviceInfoProviding {
    actual override fun readDeviceInfos(): Flow<DeviceInfoProviding> = flowOf(this)
    actual override val screenSize: Size = Size(width = 393.0f, height = 852.0f)
    actual override val operatingSystem: OperatingSystem = OperatingSystem.IOS
    actual override val safeArea: SafeArea = SafeArea(
        top = 59.0,
        leading = 0.0,
        bottom = 0.0,
        trailing = 32.0
    )
}