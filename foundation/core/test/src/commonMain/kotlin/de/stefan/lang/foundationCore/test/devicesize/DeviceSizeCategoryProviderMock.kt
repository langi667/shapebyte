package de.stefan.lang.foundationCore.test.devicesize

import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategory
import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundationCore.api.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.api.devicesize.Size

class DeviceSizeCategoryProviderMock(
    override val screenSizeProvider: ScreenSizeProviding,
    override val sizeCategory: DeviceSizeCategory,
    private val sizeCategories: List<DeviceSizeCategory>,
) : DeviceSizeCategoryProviding {

    override val screenSize: Size = screenSizeProvider.screenSize

    override fun sizeCategories(): List<DeviceSizeCategory> {
        return sizeCategories
    }
}
