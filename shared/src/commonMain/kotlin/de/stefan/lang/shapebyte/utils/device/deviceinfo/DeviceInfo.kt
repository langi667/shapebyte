package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.device.safearea.SafeArea
import de.stefan.lang.shapebyte.utils.device.safearea.SafeAreaDetector
import de.stefan.lang.shapebyte.utils.os.OperatingSystem
import kotlinx.coroutines.flow.Flow

expect class DeviceInfo(safeAreaDetector: SafeAreaDetector) : DeviceInfoProviding {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
    override val safeArea: SafeArea

    override fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
