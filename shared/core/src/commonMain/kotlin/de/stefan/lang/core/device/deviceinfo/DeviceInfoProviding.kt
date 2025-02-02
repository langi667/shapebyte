package de.stefan.lang.core.device.deviceinfo

import de.stefan.lang.core.device.devicesize.ScreenSizeProviding
import de.stefan.lang.core.device.safearea.SafeAreaProviding
import de.stefan.lang.core.os.OperatingSystemInfoProviding
import kotlinx.coroutines.flow.Flow

interface DeviceInfoProviding :
    ScreenSizeProviding,
    OperatingSystemInfoProviding,
    SafeAreaProviding {

    fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
