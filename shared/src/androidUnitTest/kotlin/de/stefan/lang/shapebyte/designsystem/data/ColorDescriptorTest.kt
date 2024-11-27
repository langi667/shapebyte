package de.stefan.lang.shapebyte.designsystem.data

import de.stefan.lang.shapebyte.utils.designsystem.data.ColorDescriptor
import de.stefan.lang.shapebyte.utils.designsystem.data.colorHexValue
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorDescriptorTest {
    @Test
    fun `should return correct Hex value`() {
        val hexColor: Long = 0x12345678
        val sut: ColorDescriptor = ColorDescriptor.Hex(hexColor)

        assertEquals(hexColor, sut.colorHexValue)
    }
}