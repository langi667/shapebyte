package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

expect class DeviceSizeCategoryProvider(
    screenSizeProvider: ScreenSizeProviding,
) : DeviceSizeCategoryProviding {
    override val screenSize: Size
    override val sizeCategory: DeviceSizeCategory
    override val screenSizeProvider: ScreenSizeProviding

    override fun sizeCategories(): List<DeviceSizeCategory>
}
