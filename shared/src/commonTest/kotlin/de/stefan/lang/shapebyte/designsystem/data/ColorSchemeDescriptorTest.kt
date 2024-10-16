package de.stefan.lang.shapebyte.designsystem.data

import kotlin.test.Test
import kotlin.test.assertEquals

class ColorSchemeDescriptorTest {

    @Test
    fun `constructor with default color`() {
        val colorCode = 0x12345678L
        val sut = ColorSchemeDescriptor(ColorDescriptor.Hex(colorCode))

        assertEquals(ColorDescriptor.Hex(colorCode), sut.defaultColor)
        assertEquals(ColorDescriptor.Hex(colorCode), sut.defaultColor)
    }
}
