package de.stefan.lang.foundationCore.device.deviceinfo

import de.stefan.lang.foundationCore.device.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.device.safearea.SafeAreaProviding
import de.stefan.lang.foundationCore.os.OperatingSystemInfoProviding
import kotlinx.coroutines.flow.Flow

interface DeviceInfoProviding :
    ScreenSizeProviding,
    OperatingSystemInfoProviding,
    SafeAreaProviding {

    fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
