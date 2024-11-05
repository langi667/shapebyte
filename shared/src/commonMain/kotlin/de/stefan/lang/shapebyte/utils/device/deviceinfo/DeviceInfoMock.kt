package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.Size

class DeviceInfoMock(
    override val screenSize: Size = Size(width = 393.0f, height = 852.0f),
) : DeviceInfoProviding
