package de.stefan.lang.foundation.presentation.contract.dimension

import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategory
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundation.core.contract.devicesize.Size
import kotlin.math.min

public class DimensionProvider(
    private val deviceSizeCategoryProvider: DeviceSizeCategoryProviding,
) {
    public val screenSize: Size
        get() = deviceSizeCategory.size

    public val deviceSizeCategory: DeviceSizeCategory
        get() = deviceSizeCategoryProvider.sizeCategory

    private val defaultSize: Size by lazy {
        deviceSizeCategoryProvider
            .sizeCategories()
            .filterIsInstance<DeviceSizeCategory.Medium>().first().size
    }

    /*
     * For Obj-C interop
     * */
    public fun withDimensionalAspect(height: Float): Float {
        return withDimensionalAspect(height, Float.MAX_VALUE)
    }

    public fun withDimensionalAspect(height: Float, max: Float): Float {
        val heightFactor = screenSize.height / defaultSize.height
        val result = height * heightFactor

        val retVal = min(max, result)
        return retVal
    }
}
