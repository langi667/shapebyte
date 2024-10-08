package de.stefan.lang.shapebyte.android.designsystem.ui.components

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import de.stefan.lang.shapebyte.android.designsystem.ui.toAndroidFontWeight
import de.stefan.lang.shapebyte.designsystem.data.FontDescriptor

// TODO: Test
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
