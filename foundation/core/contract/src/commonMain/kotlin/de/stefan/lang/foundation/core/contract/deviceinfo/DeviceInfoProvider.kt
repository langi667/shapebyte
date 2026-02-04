package de.stefan.lang.foundation.core.contract.deviceinfo

import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.os.OperatingSystemInfoProviding
import de.stefan.lang.foundation.core.contract.safearea.SafeAreaProviding
import kotlinx.coroutines.flow.Flow

public interface DeviceInfoProvider :
    ScreenSizeProviding,
    OperatingSystemInfoProviding,
    SafeAreaProviding {

    public fun readDeviceInfos(): Flow<DeviceInfoProvider>
}
