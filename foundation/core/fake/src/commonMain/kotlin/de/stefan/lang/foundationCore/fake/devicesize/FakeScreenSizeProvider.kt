package de.stefan.lang.foundationCore.fake.devicesize

import de.stefan.lang.foundationCore.api.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.api.devicesize.Size

class FakeScreenSizeProvider(override val screenSize: Size) : ScreenSizeProviding
