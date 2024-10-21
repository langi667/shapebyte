package de.stefan.lang.shapebyte.utils.device.devicesize.mocks

import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.Size

class ScreenSizeProviderMock(
    override val screenSize: Size = Size(width = 393.0f, height = 852.0f),
) : ScreenSizeProviding
