package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

public expect class DeviceSizeCategoryProviderImpl public constructor(
    screenSizeProvider: ScreenSizeProviding,
) : DeviceSizeCategoryProvider {
    public override val screenSize: Size
    public override val sizeCategory: DeviceSizeCategory
    public override val screenSizeProvider: ScreenSizeProviding

    public override fun sizeCategories(): List<DeviceSizeCategory>
}
