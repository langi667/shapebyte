package de.stefan.lang.foundation.core.fake.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

public class ScreenSizeProviderFake public constructor(override val screenSize: Size) : ScreenSizeProviding
