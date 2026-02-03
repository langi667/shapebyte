package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

expect class DeviceSizeCategoryProviderImpl(
    screenSizeProvider: ScreenSizeProviding,
) : DeviceSizeCategoryProvider {
    override val screenSize: Size
    override val sizeCategory: DeviceSizeCategory
    override val screenSizeProvider: ScreenSizeProviding

    override fun sizeCategories(): List<DeviceSizeCategory>
}
