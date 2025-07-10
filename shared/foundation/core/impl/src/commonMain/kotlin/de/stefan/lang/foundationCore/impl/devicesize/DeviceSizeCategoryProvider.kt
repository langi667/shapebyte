package de.stefan.lang.foundationCore.impl.devicesize

import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategory
import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundationCore.api.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.api.devicesize.Size

expect class DeviceSizeCategoryProvider(
    screenSizeProvider: ScreenSizeProviding,
) : DeviceSizeCategoryProviding {
    override val screenSize: Size
    override val sizeCategory: DeviceSizeCategory
    override val screenSizeProvider: ScreenSizeProviding

    override fun sizeCategories(): List<DeviceSizeCategory>
}
