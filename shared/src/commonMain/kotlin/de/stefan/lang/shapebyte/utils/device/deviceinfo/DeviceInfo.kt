package de.stefan.lang.shapebyte.utils.device.deviceinfo

import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.os.OperatingSystem

// TODO: merge safe area insets and detection
// TODO: maybe use mock for snapshot tests
expect class DeviceInfo() : DeviceInfoProviding {
    override val screenSize: Size
    override val operatingSystem: OperatingSystem
}
