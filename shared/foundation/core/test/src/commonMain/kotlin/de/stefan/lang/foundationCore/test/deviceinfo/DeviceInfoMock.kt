package de.stefan.lang.foundationCore.test.deviceinfo

import de.stefan.lang.foundationCore.api.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.api.devicesize.Size
import de.stefan.lang.foundationCore.api.os.OperatingSystem
import de.stefan.lang.foundationCore.api.safearea.SafeArea
import kotlinx.coroutines.flow.Flow

expect class DeviceInfoMock() : DeviceInfoProviding {
    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea
}
