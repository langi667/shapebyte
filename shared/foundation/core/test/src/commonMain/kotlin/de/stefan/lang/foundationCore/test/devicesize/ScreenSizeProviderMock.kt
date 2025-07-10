package de.stefan.lang.foundationCore.test.devicesize

import de.stefan.lang.foundationCore.api.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.api.devicesize.Size

class ScreenSizeProviderMock(override val screenSize: Size) : ScreenSizeProviding
