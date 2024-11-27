package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import de.stefan.lang.shapebyte.utils.designsystem.data.FontDescriptor

fun FontDescriptor.toTextStyle(): TextStyle {
    when (this) {
        is FontDescriptor.System -> {
            return TextStyle(
                fontSize = size.sp,
                fontWeight = weight.toAndroidFontWeight(),
            )
        }
    }
}
