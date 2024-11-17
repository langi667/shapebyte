package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.os.OperatingSystem

class DeviceInfoMock(
    override val screenSize: Size = Size(width = 393.0f, height = 852.0f),
    override val operatingSystem: OperatingSystem = OperatingSystem.Android,
) : DeviceInfoProviding
