package de.stefan.lang.foundationCore.impl.deviceinfo

import de.stefan.lang.foundationCore.api.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.api.devicesize.Size
import de.stefan.lang.foundationCore.api.os.OperatingSystem
import de.stefan.lang.foundationCore.api.safearea.SafeArea
import de.stefan.lang.foundationCore.impl.safearea.SafeAreaDetector
import kotlinx.coroutines.flow.Flow

expect class DeviceInfo(safeAreaDetector: SafeAreaDetector) : DeviceInfoProviding {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea

    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
