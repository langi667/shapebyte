package de.stefan.lang.shapebyte.android.shared.ui.content.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import kotlinx.collections.immutable.toImmutableList

@DslMarker
annotation class ContentViewItemTypeDsl

// TODO: rename to ContentViewItemTypeBuilder
@ContentViewItemTypeDsl
class ContentViewItemBuilder {
    private val items = mutableListOf<ContentViewItem>()

    fun build() = items.toImmutableList()

    fun sectionTitle(title: String) {
        items.add(ContentViewItem.SectionTitle(title))
    }

    fun spacer(height: Dp) {
        items.add(ContentViewItem.Spacer(height))
    }

    fun <T> data(
        id: String,
        data: T?,
        stableKey: String? = null,
        content: @Composable (() -> Unit) = {
            Text("$id for Key: ${stableKey ?: "null"} with Data: $data")
        },
    ) {
        items.add(ContentViewItem.Data<T>(id, data, stableKey, content))
    }
}