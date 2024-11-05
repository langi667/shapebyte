package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.Size

expect class DeviceInfo() : DeviceInfoProviding {
    override val screenSize: Size
}
