package de.stefan.lang.foundation.core.fake.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size

class DeviceSizeCategoryProviderFake(
    override val screenSizeProvider: ScreenSizeProviding,
    override val sizeCategory: DeviceSizeCategory,
    private val sizeCategories: List<DeviceSizeCategory>,
) : DeviceSizeCategoryProviding {

    override val screenSize: Size = screenSizeProvider.screenSize

    override fun sizeCategories(): List<DeviceSizeCategory> {
        return sizeCategories
    }
}
