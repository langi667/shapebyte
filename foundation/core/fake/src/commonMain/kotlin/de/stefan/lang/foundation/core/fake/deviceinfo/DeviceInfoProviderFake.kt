package de.stefan.lang.foundation.core.fake.deviceinfo

import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.devicesize.Size
import de.stefan.lang.foundation.core.contract.os.OperatingSystem
import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import kotlinx.coroutines.flow.Flow

expect class DeviceInfoProviderFake() : DeviceInfoProvider {
    override fun readDeviceInfos(): Flow<DeviceInfoProvider>
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea
}
