package de.stefan.lang.foundationUi.api.dimension

import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategory
import de.stefan.lang.foundationCore.api.devicesize.Size
import de.stefan.lang.foundationCore.test.devicesize.DeviceSizeCategoryProviderMock
import de.stefan.lang.foundationCore.test.devicesize.ScreenSizeProviderMock
import kotlin.test.Test
import kotlin.test.assertEquals

class DimensionProviderTest {
    private val sizeCategories = listOf(
        DeviceSizeCategory.XSmall(size = Size(width = 240f, height = 480f)),
        DeviceSizeCategory.Small(size = Size(width = 360f, height = 640f)),
        DeviceSizeCategory.Medium(size = Size(width = 480f, height = 800f)),
        DeviceSizeCategory.Large(size = Size(width = 560f, height = 900f)),
    )

    private val testDeviceCategory = sizeCategories
        .filterIsInstance<DeviceSizeCategory.Medium>()
        .first()

    private val xSmallDeviceCategory = sizeCategories
        .filterIsInstance<DeviceSizeCategory.XSmall>()
        .first()

    @Test
    fun `should return smaller dimension`() {
        val screenSize = xSmallDeviceCategory.size
        val sut = createSUT(screenSize, xSmallDeviceCategory)

        val dimension = 84f
        val result = sut.withDimensionalAspect(dimension)
        assertEquals(result, 50.4f)
    }

    @Test
    fun `should return dimension if max not set`() {
        val screenSize = testDeviceCategory.size
        val sut = createSUT(screenSize, testDeviceCategory)

        val dimension = 84f
        val result = sut.withDimensionalAspect(dimension)
        assertEquals(result, dimension)
    }

    @Test
    fun `should return dimension if max is greater`() {
        val screenSize = testDeviceCategory.size
        val sut = createSUT(screenSize, testDeviceCategory)

        val dimension = 84f
        val result = sut.withDimensionalAspect(dimension, dimension * 2)
        assertEquals(result, dimension)
    }

    @Test
    fun `should return max if dimension is greater`() {
        val screenSize = Size(width = 400.0f, height = 800.0f)
        val sut = createSUT(screenSize, testDeviceCategory)

        val dimension = 84f
        val max = dimension / 2
        val result = sut.withDimensionalAspect(dimension, max)
        assertEquals(result, max)
    }

    private fun createSUT(
        screenSize: Size,
        deviceSizeCategory: DeviceSizeCategory,
    ): DimensionProvider {
        val deviceSizeCategoryProvider = DeviceSizeCategoryProviderMock(
            ScreenSizeProviderMock(screenSize),
            deviceSizeCategory,
            sizeCategories,
        )

        return DimensionProvider(deviceSizeCategoryProvider)
    }
}
