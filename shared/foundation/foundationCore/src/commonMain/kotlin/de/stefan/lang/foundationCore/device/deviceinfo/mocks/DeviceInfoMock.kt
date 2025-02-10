package de.stefan.lang.foundationCore.device.deviceinfo.mocks

import de.stefan.lang.foundationCore.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.device.devicesize.Size
import de.stefan.lang.foundationCore.device.safearea.SafeArea
import de.stefan.lang.foundationCore.os.OperatingSystem
import kotlinx.coroutines.flow.Flow

expect class DeviceInfoMock() : DeviceInfoProviding {
    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea
}
