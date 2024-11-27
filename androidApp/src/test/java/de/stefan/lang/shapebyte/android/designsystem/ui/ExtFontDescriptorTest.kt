package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.ui.unit.sp
import de.stefan.lang.shapebyte.utils.designsystem.data.FontDescriptor
import de.stefan.lang.shapebyte.utils.designsystem.data.FontWeight
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtFontDescriptorTest {
    @Test
    fun `Test testToTextStyle should create correct TextStyle`() {
        val fontDescriptor = FontDescriptor.System(
            size = 12,
            weight = FontWeight.Regular,
        )

        val result = fontDescriptor.toTextStyle()
        assertEquals(12.sp, result.fontSize)
        assertEquals(FontWeight.Regular.toAndroidFontWeight(), result.fontWeight)
    }
}
