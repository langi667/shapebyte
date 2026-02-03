package de.stefan.lang.foundation.core.fake.deviceinfo

import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size
import de.stefan.lang.foundation.core.contract.os.OperatingSystem
import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import kotlinx.coroutines.flow.Flow

expect class DeviceInfoFake() : DeviceInfoProviding {
    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea
}
