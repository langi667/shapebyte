package de.stefan.lang.shapebyte.device.devicesize

import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfoMock
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategory
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.device.devicesize.mocks.DeviceSizeCategoryProviderMock
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertIsNot

class DeviceSizeCategoryProviderTest {

    val xSmall = DeviceSizeCategory.XSmall(size = Size(width = 240f, height = 480f))
    val small = DeviceSizeCategory.Small(size = Size(width = 360f, height = 640f))
    val medium = DeviceSizeCategory.Medium(size = Size(width = 480f, height = 800f))
    val large = DeviceSizeCategory.Large(size = Size(width = 560f, height = 900f))

    private val sizeCategories = listOf(
        xSmall,
        small,
        medium,
        large,
    )

    @Test
    fun `deviceSizeCategoryForSize should return xSmall`() {
        val sut = createSUT(Size(width = 240.0f, height = 480.0f), xSmall)

        listOf(
            Size(width = 240.0f, height = 80.0f),
            Size(width = 240.0f, height = 120.0f),
            Size(width = 240.0f, height = 450.0f),
            xSmall.size,
        ).forEach {
            assertIs<DeviceSizeCategory.XSmall>(sut.deviceSizeCategoryForSize(it))
        }

        assertIsNot<DeviceSizeCategory.XSmall>(
            sut.deviceSizeCategoryForSize(
                size = xSmall.size.copy(height = xSmall.size.height + 1),
            ),
        )
    }

    @Test
    fun `deviceSizeCategoryForSize should return small`() {
        val sut = createSUT(Size(width = 240.0f, height = 480.0f), xSmall)

        listOf(
            Size(width = 360f, height = 500f),
            Size(width = 240.0f, height = 540f),
            Size(width = 360f, height = 600f),
            small.size,
        ).forEach {
            assertIs<DeviceSizeCategory.Small>(sut.deviceSizeCategoryForSize(it))
        }

        assertIsNot<DeviceSizeCategory.Small>(
            sut.deviceSizeCategoryForSize(
                size = small.size.copy(height = small.size.height + 1),
            ),
        )
    }

    @Test
    fun `deviceSizeCategoryForSize should return medium`() {
        val sut = createSUT(Size(width = 240.0f, height = 480.0f), xSmall)

        listOf(
            small.size.copy(height = small.size.height + 1),
            Size(width = 360f, height = 680f),
            Size(width = 240.0f, height = 720f),
            Size(width = 360f, height = 790f),
            medium.size,
        ).forEach {
            assertIs<DeviceSizeCategory.Medium>(sut.deviceSizeCategoryForSize(it))
        }

        assertIsNot<DeviceSizeCategory.Small>(
            sut.deviceSizeCategoryForSize(
                size = medium.size.copy(height = medium.size.height + 1),
            ),
        )
    }

    @Test
    fun `deviceSizeCategoryForSize should return large`() {
        val sut = createSUT(Size(width = 240.0f, height = 480.0f), xSmall)

        listOf(
            medium.size.copy(height = medium.size.height + 1),
            Size(width = 560f, height = 880f),
            large.size,
            Size(width = 560f, height = 1880f),
        ).forEach {
            assertIs<DeviceSizeCategory.Large>(sut.deviceSizeCategoryForSize(it))
        }
    }

    private fun createSUT(
        screenSize: Size,
        category: DeviceSizeCategory,
    ): DeviceSizeCategoryProviding {
        val screenSizeProviderMock = DeviceInfoMock(screenSize = screenSize)
        return DeviceSizeCategoryProviderMock(
            screenSizeProvider = screenSizeProviderMock,
            sizeCategory = category,
            sizeCategories = sizeCategories,
        )
    }
}
