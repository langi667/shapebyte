package de.stefan.lang.foundation.core.implementation.deviceinfo

import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.devicesize.Size
import de.stefan.lang.foundation.core.contract.os.OperatingSystem
import de.stefan.lang.foundation.core.contract.safearea.SafeArea
import de.stefan.lang.foundation.core.implementation.safearea.SafeAreaDetector
import kotlinx.coroutines.flow.Flow

public expect class DeviceInfoProviderImpl public constructor(safeAreaDetector: SafeAreaDetector) : DeviceInfoProvider {
    public override val screenSize: Size
    public override val operatingSystem: OperatingSystem
    public override val safeArea: SafeArea

    public override fun readDeviceInfos(): Flow<DeviceInfoProvider>
}
