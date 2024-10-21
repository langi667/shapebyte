package de.stefan.lang.shapebyte.utils.device.devicesize.mocks

import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategory
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.Size

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
