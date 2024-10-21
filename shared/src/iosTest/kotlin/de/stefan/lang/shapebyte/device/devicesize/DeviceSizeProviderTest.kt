package de.stefan.lang.shapebyte.device.devicesize

import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.shapebyte.utils.device.devicesize.Size
import de.stefan.lang.shapebyte.utils.device.devicesize.iPhoneSizeBounds
import de.stefan.lang.shapebyte.utils.device.devicesize.mocks.ScreenSizeProviderMock
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DeviceSizeProviderTest {
    @Test
    fun `screen category should be xSmall`() {
        val sut = createSUT(iPhoneSizeBounds.xSmall.category.size)
        assertTrue(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be xSmall with in between`() {
        val sut = createSUT(
            Size(
                width = iPhoneSizeBounds.xSmall.category.size.width,
                height = iPhoneSizeBounds.xSmall.category.size.height - 10
            )
        )

        assertTrue(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be small`() {
        val sut = createSUT(iPhoneSizeBounds.small.category.size)

        assertFalse(sut.sizeCategory.isXSmall)
        assertTrue(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be small with in between`() {
        val sut = createSUT(
            Size(
                width = iPhoneSizeBounds.small.category.size.width,
                height = iPhoneSizeBounds.small.category.size.height - 10
            )
        )

        assertFalse(sut.sizeCategory.isXSmall)
        assertTrue(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be medium`() {
        val sut = createSUT(iPhoneSizeBounds.medium.category.size)

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertTrue(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be medium with in between`() {
        val sut = createSUT(
            Size(
                width = iPhoneSizeBounds.medium.category.size.width,
                height = iPhoneSizeBounds.medium.category.size.height - 10
            )
        )

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertTrue(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be large`() {
        val sut = createSUT(iPhoneSizeBounds.large.category.size)

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertTrue(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be large with in between`() {
        val sut = createSUT(
            Size(
                width = iPhoneSizeBounds.large.category.size.width,
                height = iPhoneSizeBounds.large.category.size.height - 10
            )
        )

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertTrue(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be large when height is larger than defined large height`() {
        val sut = createSUT(
            Size(
                width = iPhoneSizeBounds.large.category.size.width,
                height = iPhoneSizeBounds.large.category.size.height + 100
            )
        )

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertTrue(sut.sizeCategory.isLarge)
    }

    private fun createSUT(size: Size): DeviceSizeCategoryProvider =
        DeviceSizeCategoryProvider(ScreenSizeProviderMock(size))

}