package de.stefan.lang.foundationCore.api.devicesize

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DeviceSizeCategoryTest {
    private val xSmall = DeviceSizeCategory.XSmall(Size(100f, 200f))
    private val small = DeviceSizeCategory.Small(Size(300f, 500f))
    private val medium = DeviceSizeCategory.Medium(Size(500f, 750f))
    private val large = DeviceSizeCategory.Large(Size(800f, 1280f))

    @Test
    fun `isXSmall returns true for XSmall`() {
        assertTrue(xSmall.isXSmall)
        assertFalse(small.isXSmall)
        assertFalse(medium.isXSmall)
        assertFalse(large.isXSmall)
    }

    @Test
    fun `isSmall returns true for Small`() {
        assertFalse(xSmall.isSmall)
        assertTrue(small.isSmall)
        assertFalse(medium.isSmall)
        assertFalse(large.isSmall)
    }

    @Test
    fun `isMedium returns true for Medium`() {
        assertFalse(xSmall.isMedium)
        assertFalse(small.isMedium)
        assertTrue(medium.isMedium)
        assertFalse(large.isMedium)
    }

    @Test
    fun `isLarge returns true for Large`() {
        assertFalse(xSmall.isLarge)
        assertFalse(small.isLarge)
        assertFalse(medium.isLarge)
        assertTrue(large.isLarge)
    }
}
