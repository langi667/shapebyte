package de.stefan.lang.shapebyte.android.designsystem.ui

import androidx.compose.ui.graphics.Color
import de.stefan.lang.shapebyte.utils.designsystem.data.ColorDescriptor
import de.stefan.lang.shapebyte.utils.designsystem.data.colorHexValue

val ColorDescriptor.color: Color
    get() = when (this) {
        is ColorDescriptor.Hex -> Color(this.colorHexValue)
        is ColorDescriptor.NamedAsset -> throw IllegalArgumentException(
            "NamedAsset is not supported for returning native Android Color",
        )
    }
