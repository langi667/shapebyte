package de.stefan.lang.foundation.core.contract.devicesize

public interface DeviceSizeCategoryProvider {
    public val screenSize: Size
    public val sizeCategory: DeviceSizeCategory
    public val screenSizeProvider: ScreenSizeProviding

    public fun sizeCategories(): List<DeviceSizeCategory>

    public fun deviceSizeCategoryForSize(size: Size): DeviceSizeCategory {
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
