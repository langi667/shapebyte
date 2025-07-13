package de.stefan.lang.foundationCore.impl.devicesize

import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategory
import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundationCore.api.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.api.devicesize.Size

actual class DeviceSizeCategoryProvider actual constructor(
    actual override val screenSizeProvider: ScreenSizeProviding,
) : DeviceSizeCategoryProviding {
    actual override val screenSize: Size by lazy { screenSizeProvider.screenSize }
    actual override val sizeCategory: DeviceSizeCategory by lazy {
        deviceSizeCategoryForSize(this.screenSize)
    }

    actual override fun sizeCategories(): List<DeviceSizeCategory> = AndroidSizeBounds
        .entries
        .map { it.category }
}
