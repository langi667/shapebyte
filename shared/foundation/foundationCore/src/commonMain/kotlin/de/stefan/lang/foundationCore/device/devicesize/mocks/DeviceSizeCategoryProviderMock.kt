package de.stefan.lang.foundationCore.device.devicesize.mocks

import de.stefan.lang.foundationCore.device.devicesize.DeviceSizeCategory
import de.stefan.lang.foundationCore.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundationCore.device.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.device.devicesize.Size

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
