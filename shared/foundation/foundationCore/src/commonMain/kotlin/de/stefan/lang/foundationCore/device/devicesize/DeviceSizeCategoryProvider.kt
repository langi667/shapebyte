package de.stefan.lang.foundationCore.device.devicesize

interface DeviceSizeCategoryProviding {
    val screenSize: Size
    val sizeCategory: DeviceSizeCategory
    val screenSizeProvider: ScreenSizeProviding

    fun sizeCategories(): List<DeviceSizeCategory>

    fun deviceSizeCategoryForSize(size: Size): DeviceSizeCategory {
        val height = size.height.toInt()
        var prevHeight = 0

        val sizeCategories = sizeCategories()
            .sortedBy { it.size.height }

        sizeCategories.forEach {
            if (height in (prevHeight + 1)..it.size.height.toInt()) {
                return it
            }

            prevHeight = it.size.height.toInt()
        }

        return sizeCategories.last()
    }
}

expect class DeviceSizeCategoryProvider(
    screenSizeProvider: ScreenSizeProviding,
) : DeviceSizeCategoryProviding {
    override val screenSize: Size
    override val sizeCategory: DeviceSizeCategory
    override val screenSizeProvider: ScreenSizeProviding

    override fun sizeCategories(): List<DeviceSizeCategory>
}
