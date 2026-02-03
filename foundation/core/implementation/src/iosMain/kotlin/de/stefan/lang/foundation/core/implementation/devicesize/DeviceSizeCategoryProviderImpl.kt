package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

actual class DeviceSizeCategoryProviderImpl actual constructor(
    actual override val screenSizeProvider: ScreenSizeProviding
): DeviceSizeCategoryProvider {
    actual override val screenSize: Size by lazy { screenSizeProvider.screenSize }
    actual override val sizeCategory: DeviceSizeCategory by lazy {
        deviceSizeCategoryForSize(this.screenSize)
    }

    actual override fun sizeCategories(): List<DeviceSizeCategory> = iPhoneSizeBounds
        .entries
        .map { it.category }
}
