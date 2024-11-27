package de.stefan.lang.shapebyte.utils.dimension

import de.stefan.lang.shapebyte.utils.designsystem.data.Dimension
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategory
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import kotlin.math.min

class DimensionProvider(
    private val deviceSizeCategoryProvider: DeviceSizeCategoryProviding,
) {
    val screenSize: Size
        get() = deviceSizeCategory.size

    val deviceSizeCategory: DeviceSizeCategory
        get() = deviceSizeCategoryProvider.sizeCategory

    private val defaultSize: Size by lazy {
        deviceSizeCategoryProvider
            .sizeCategories()
            .filterIsInstance<DeviceSizeCategory.Medium>().first().size
    }

    /*
     * For Obj-C interop
     * */
    fun withDimensionalAspect(height: Float): Float {
        return withDimensionalAspect(height, Float.MAX_VALUE)
    }

    fun withDimensionalAspect(height: Float, max: Float): Float {
        val heightFactor = screenSize.height / defaultSize.height
        val result = height * heightFactor

        val retVal = min(max, result)
        return retVal
    }
}
