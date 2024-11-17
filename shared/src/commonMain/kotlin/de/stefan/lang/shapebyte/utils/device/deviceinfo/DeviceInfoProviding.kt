package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.os.OperatingSystemInfoProviding

interface DeviceInfoProviding : ScreenSizeProviding, OperatingSystemInfoProviding {
    // TODO: add more infos like OS, OS Name ...
}
