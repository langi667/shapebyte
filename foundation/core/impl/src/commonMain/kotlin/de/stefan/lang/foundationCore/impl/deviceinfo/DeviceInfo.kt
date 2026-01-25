package de.stefan.lang.foundationCore.impl.deviceinfo

import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size
import de.stefan.lang.foundation.core.contract.os.OperatingSystem
import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import de.stefan.lang.foundationCore.impl.safearea.SafeAreaDetector
import kotlinx.coroutines.flow.Flow

expect class DeviceInfo(safeAreaDetector: SafeAreaDetector) : DeviceInfoProviding {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea

    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
