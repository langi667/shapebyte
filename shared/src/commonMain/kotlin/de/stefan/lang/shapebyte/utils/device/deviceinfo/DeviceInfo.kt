package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.os.OperatingSystem

expect class DeviceInfo() : DeviceInfoProviding {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
}
