package de.stefan.lang.shapebyte.android.designsystem.ui

import de.stefan.lang.shapebyte.utils.designsystem.data.FontWeight
import androidx.compose.ui.text.font.FontWeight as AndroidFontWeight

fun FontWeight.toAndroidFontWeight(): AndroidFontWeight {
    return when (this) {
        FontWeight.UltraLight -> AndroidFontWeight.ExtraLight
        FontWeight.Thin -> AndroidFontWeight.Thin
        FontWeight.Light -> AndroidFontWeight.Light
        FontWeight.Regular -> AndroidFontWeight.Normal
        FontWeight.Medium -> AndroidFontWeight.Medium
        FontWeight.Semibold -> AndroidFontWeight.SemiBold
        FontWeight.Bold -> AndroidFontWeight.Bold
        FontWeight.Heavy -> AndroidFontWeight.ExtraBold
        FontWeight.Black -> AndroidFontWeight.Black
    }
}
