package de.stefan.lang.foundation.core.implementation.devicesize

import de.stefan.lang.foundation.core.contract.devicesize.Size
import de.stefan.lang.foundation.core.fake.devicesize.ScreenSizeProviderFake
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DeviceSizeProviderImplTest {
    @Test
    fun `screen category should be xSmall`() {
        val sut = createSUT(AndroidSizeBounds.XSmall.category.size)
        assertTrue(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be xSmall with in between`() {
        val sut = createSUT(
            Size(
                width = AndroidSizeBounds.XSmall.category.size.width,
                height = AndroidSizeBounds.XSmall.category.size.height - 10
            )
        )

        assertTrue(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be small`() {
        val sut = createSUT(AndroidSizeBounds.Small.category.size)

        assertFalse(sut.sizeCategory.isXSmall)
        assertTrue(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be small with in between`() {
        val sut = createSUT(
            Size(
                width = AndroidSizeBounds.Small.category.size.width,
                height = AndroidSizeBounds.Small.category.size.height - 10
            )
        )

        assertFalse(sut.sizeCategory.isXSmall)
        assertTrue(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be medium`() {
        val sut = createSUT(AndroidSizeBounds.Medium.category.size)

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertTrue(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be medium with in between`() {
        val sut = createSUT(
            Size(
                width = AndroidSizeBounds.Medium.category.size.width,
                height = AndroidSizeBounds.Medium.category.size.height - 10
            )
        )

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertTrue(sut.sizeCategory.isMedium)
        assertFalse(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be large`() {
        val sut = createSUT(AndroidSizeBounds.Large.category.size)

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertTrue(sut.sizeCategory.isLarge)
    }

    @Test
    fun `screen category should be large with in between`() {
        val sut = createSUT(
            Size(
                width = AndroidSizeBounds.Large.category.size.width,
                height = AndroidSizeBounds.Large.category.size.height - 10
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
                width = AndroidSizeBounds.Large.category.size.width,
                height = AndroidSizeBounds.Large.category.size.height + 100
            )
        )

        assertFalse(sut.sizeCategory.isXSmall)
        assertFalse(sut.sizeCategory.isSmall)
        assertFalse(sut.sizeCategory.isMedium)
        assertTrue(sut.sizeCategory.isLarge)
    }

    private fun createSUT(size: Size): DeviceSizeCategoryProviderImpl {
        val screenSizeProvider = ScreenSizeProviderFake(size)
        return DeviceSizeCategoryProviderImpl(screenSizeProvider)
    }
}