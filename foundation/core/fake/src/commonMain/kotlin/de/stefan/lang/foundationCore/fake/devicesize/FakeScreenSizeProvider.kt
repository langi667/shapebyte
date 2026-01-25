package de.stefan.lang.foundationCore.fake.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

class FakeScreenSizeProvider(override val screenSize: Size) : ScreenSizeProviding
