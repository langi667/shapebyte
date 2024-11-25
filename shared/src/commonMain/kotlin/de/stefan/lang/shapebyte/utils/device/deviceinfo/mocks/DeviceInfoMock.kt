package de.stefan.lang.shapebyte.utils.device.deviceinfo.mocks

import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.device.safearea.SafeArea
import de.stefan.lang.shapebyte.utils.os.OperatingSystem
import kotlinx.coroutines.flow.Flow

expect class DeviceInfoMock() : DeviceInfoProviding {
    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea
}
