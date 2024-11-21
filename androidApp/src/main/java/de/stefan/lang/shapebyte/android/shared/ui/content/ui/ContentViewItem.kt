package de.stefan.lang.shapebyte.android.shared.ui.content.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

sealed interface ContentViewItem {
    val id: String
    val stableKey: String? get() = null
    val contentType: Any? get() = this::class.simpleName

    data class Data<T>(
        override val id: String,
        val data: T?,
        override val stableKey: String? = null,
        val content: @Composable () -> Unit,
    ) : ContentViewItem

    data class Spacer(
        val height: Dp,
    ) : ContentViewItem {
        override val id: String = "ContentViewItem.$contentType"
        override val stableKey: String? = null
    }

    data class SectionTitle(
        val title: String,
        override val stableKey: String? = title,
    ) : ContentViewItem {
        override val id: String = "ContentViewItem.$contentType"
    }
}
