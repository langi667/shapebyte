package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

public actual class DeviceSizeCategoryProviderImpl actual constructor(
    public actual override val screenSizeProvider: ScreenSizeProviding
): DeviceSizeCategoryProvider {
    public actual override val screenSize: Size by lazy { screenSizeProvider.screenSize }
    public actual override val sizeCategory: DeviceSizeCategory by lazy {
        deviceSizeCategoryForSize(this.screenSize)
    }

    public actual override fun sizeCategories(): List<DeviceSizeCategory> = iPhoneSizeBounds
        .entries
        .map { it.category }
}
