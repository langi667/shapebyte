package de.stefan.lang.core.device.deviceinfo

import de.stefan.lang.core.device.devicesize.Size
import de.stefan.lang.core.device.safearea.SafeArea
import de.stefan.lang.core.device.safearea.SafeAreaDetector
import de.stefan.lang.core.os.OperatingSystem

import kotlinx.coroutines.flow.Flow

expect class DeviceInfo(safeAreaDetector: SafeAreaDetector) : DeviceInfoProviding {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea

    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
