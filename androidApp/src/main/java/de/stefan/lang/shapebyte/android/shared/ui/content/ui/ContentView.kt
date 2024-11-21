package de.stefan.lang.shapebyte.android.shared.ui.content.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.Head3
import de.stefan.lang.shapebyte.android.shared.ui.background.BackgroundView
import de.stefan.lang.shapebyte.android.shared.ui.header.HeaderView
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    onScroll: (
        scrollOffset: Dp,
        minimumHeaderHeight: Dp,
    ) -> Unit = { _, _ -> },
    items: ContentViewItemBuilder.() -> Unit,
) {
    ContentView(
        modifier = modifier,
        onScroll = onScroll,
        items = ContentViewItemBuilder().apply(items).build(),
    )
}

@Composable
fun ContentView(
    items: ImmutableList<ContentViewItem>,
    modifier: Modifier = Modifier,
    onScroll: (
        scrollOffset: Dp,
        minimumHeaderHeight: Dp,
    ) -> Unit = { _, _ -> },
) {
    val itemContent: @Composable LazyItemScope.(item: ContentViewItem) -> Unit = { item ->
        when (item) {
            is ContentViewItem.Spacer -> {
                Spacer(item)
            }

            is ContentViewItem.SectionTitle -> {
                SectionTitle(item)
            }

            is ContentViewItem.Data<*> -> {
                item.content()
            }
        }
    }

    val key: ((index: Int, item: ContentViewItem) -> Any) = { index, item ->
        item.stableKey ?: index
    }

    val contentType: (item: ContentViewItem) -> Any? = { item ->
        item.contentType
    }

    BackgroundView(
        modifier = modifier.fillMaxSize(),
        headerContent = { minHeight, maxHeight, currentHeight ->
            HeaderView(minHeight, maxHeight, currentHeight)
        },
        onScroll = onScroll,
        items = items,
        key = key,
        contentType = contentType,
        itemContent = itemContent,
    )
}

@Composable
private fun Spacer(item: ContentViewItem.Spacer) {
    Spacer(modifier = Modifier.height(item.height))
}

@Composable
private fun SectionTitle(item: ContentViewItem.SectionTitle) = WithTheme { theme, _ ->
    Head3(
        item.title,
        modifier = Modifier.padding(horizontal = theme.spacing.small.dp),
    )
}

@Preview
@Composable
fun ContentViewPreview() {
    ContentView(
        items = {
            sectionTitle("Section 1")
            spacer(60.dp)
            sectionTitle("Section 2")
        },
    )
}
