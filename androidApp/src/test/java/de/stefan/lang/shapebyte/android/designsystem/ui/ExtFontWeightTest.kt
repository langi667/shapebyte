package de.stefan.lang.shapebyte.android.designsystem.ui

import de.stefan.lang.shapebyte.utils.designsystem.data.FontWeight
import junit.framework.TestCase.assertEquals
import kotlin.test.Test
import androidx.compose.ui.text.font.FontWeight as AndroidFontWeight

class ExtFontWeightTest {
    @Test
    fun `toAndroidFontWeight should return correct AndroidFontWeight`() {
        assertEquals(AndroidFontWeight.ExtraLight, FontWeight.UltraLight.toAndroidFontWeight())
        assertEquals(AndroidFontWeight.Thin, FontWeight.Thin.toAndroidFontWeight())
        assertEquals(AndroidFontWeight.Light, FontWeight.Light.toAndroidFontWeight())

        assertEquals(AndroidFontWeight.Normal, FontWeight.Regular.toAndroidFontWeight())
        assertEquals(AndroidFontWeight.Medium, FontWeight.Medium.toAndroidFontWeight())
        assertEquals(AndroidFontWeight.SemiBold, FontWeight.Semibold.toAndroidFontWeight())

        assertEquals(AndroidFontWeight.Bold, FontWeight.Bold.toAndroidFontWeight())
        assertEquals(AndroidFontWeight.ExtraBold, FontWeight.Heavy.toAndroidFontWeight())
        assertEquals(AndroidFontWeight.Black, FontWeight.Black.toAndroidFontWeight())
    }
}
