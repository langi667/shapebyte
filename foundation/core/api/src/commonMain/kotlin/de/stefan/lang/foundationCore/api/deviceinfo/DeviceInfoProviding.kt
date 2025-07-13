package de.stefan.lang.foundationCore.api.deviceinfo

import de.stefan.lang.foundationCore.api.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.api.os.OperatingSystemInfoProviding
import de.stefan.lang.foundationCore.api.safearea.SafeAreaProviding
import kotlinx.coroutines.flow.Flow

interface DeviceInfoProviding :
    ScreenSizeProviding,
    OperatingSystemInfoProviding,
    SafeAreaProviding {

    fun readDeviceInfos(): Flow<DeviceInfoProviding>
}
