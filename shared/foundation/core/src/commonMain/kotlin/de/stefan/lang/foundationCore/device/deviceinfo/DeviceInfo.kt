package de.stefan.lang.foundationCore.device.deviceinfo

import de.stefan.lang.foundationCore.device.devicesize.Size
import de.stefan.lang.foundationCore.device.safearea.SafeArea
import de.stefan.lang.foundationCore.device.safearea.SafeAreaDetector
import de.stefan.lang.foundationCore.os.OperatingSystem
import kotlinx.coroutines.flow.Flow

expect class DeviceInfo(safeAreaDetector: SafeAreaDetector) : DeviceInfoProviding {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea

    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
