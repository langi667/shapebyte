package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.device.safearea.SafeAreaProviding
import de.stefan.lang.shapebyte.utils.os.OperatingSystemInfoProviding
import kotlinx.coroutines.flow.Flow

interface DeviceInfoProviding :
    ScreenSizeProviding,
    OperatingSystemInfoProviding,
    SafeAreaProviding {

    fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
