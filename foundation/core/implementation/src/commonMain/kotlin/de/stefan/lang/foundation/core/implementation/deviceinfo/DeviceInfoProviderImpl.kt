package de.stefan.lang.foundation.core.implementation.deviceinfo

import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.devicesize.Size
import de.stefan.lang.foundation.core.contract.os.OperatingSystem
import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import de.stefan.lang.foundation.core.implementation.safearea.SafeAreaDetector
import kotlinx.coroutines.flow.Flow

expect class DeviceInfoProviderImpl(safeAreaDetector: SafeAreaDetector) : DeviceInfoProvider {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea

    override fun readDeviceInfos(): Flow<DeviceInfoProvider>
}
