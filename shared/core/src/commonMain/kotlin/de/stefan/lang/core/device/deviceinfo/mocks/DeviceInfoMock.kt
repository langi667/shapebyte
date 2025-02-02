package de.stefan.lang.core.device.deviceinfo.mocks

import de.stefan.lang.core.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.core.device.devicesize.Size
import de.stefan.lang.core.device.safearea.SafeArea
import de.stefan.lang.core.os.OperatingSystem
import kotlinx.coroutines.flow.Flow

expect class DeviceInfoMock() : DeviceInfoProviding {
    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea
}
