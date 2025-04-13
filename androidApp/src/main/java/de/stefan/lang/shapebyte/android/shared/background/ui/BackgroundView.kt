package de.stefan.lang.shapebyte.android.shared.background.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.zIndex
import de.stefan.lang.designsystem.theme.ThemeAdditions
import de.stefan.lang.shapebyte.android.shared.header.ui.HeaderView
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer
import de.stefan.lang.shapebyte.android.shared.shapes.ui.Arc
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
@Suppress("MagicNumber")
fun <T> BackgroundView(
    items: ImmutableList<T>,
    modifier: Modifier = Modifier,
    headerContent: @Composable (
        minHeight: Dp,
        maxHeight: Dp,
        currentHeight: Dp,
    ) -> Unit = { _, _, _ -> },
    onScroll: (
        scrollOffset: Dp,
        minimumHeaderHeight: Dp,
    ) -> Unit = { _, _ -> },
    key: ((index: Int, item: T) -> Any)? = null,
    contentType: (item: T) -> Any? = { null },
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    val defaultArcHeight = remember { ThemeAdditions.dimensions.small.dp * 1.5f }
    val arcHeight = remember { mutableStateOf(defaultArcHeight) }

    val viewSize = remember { mutableStateOf(DpSize.Zero) }
    val density = LocalDensity.current

    val maximumHeaderHeight = ThemeAdditions.dimensions.large.dp
    val minimumHeaderHeight = maximumHeaderHeight / 2

    val scrollOffset = remember { mutableStateOf(0.dp) }
    val animatedArcHeight by animateDpAsState(
        targetValue = arcHeight.value - scrollOffset.value,
        label = "arcHeight",
    )

    val headerHeight =
        max(minimumHeaderHeight, maximumHeaderHeight - (scrollOffset.value))
    val listState = rememberLazyListState()

    val reservedKey = "__BackgroundView-RESERVED_KEY_-TOP_ITEMS__"
    val bgViewItems = listOf(reservedKey)
    val allItems = bgViewItems + items

    val bgKey: ((index: Int) -> Any) = { index ->
        if (index < bgViewItems.size) {
            reservedKey
        } else {
            val itemIndex = index - bgViewItems.size
            val item = items.getOrNull(itemIndex)
            item?.let { key?.invoke(itemIndex, it) } ?: index
        }
    }

    val bgContentType: (index: Int) -> Any? = { index ->
        if (index < bgViewItems.size) {
            reservedKey
        } else {
            val item = items.getOrNull(index - bgViewItems.size)
            item?.let { contentType.invoke(it) }
        }
    }

    val bgItemContent: @Composable LazyItemScope.(index: Int) -> Unit = { index ->
        if (index < bgViewItems.size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maximumHeaderHeight),
            ) { /*No OP*/ }

            Arc(
                Modifier
                    .fillMaxWidth(),
                height = animatedArcHeight,
                width = viewSize.value.width,
                color = MaterialTheme.colorScheme.background,
            )
        } else {
            items.getOrNull(index - bgViewItems.size)?.let {
                itemContent(it)
            }
        }
    }

    LaunchedEffect("ListState", onScroll, listState) {
        snapshotFlow {
            val index = listState.firstVisibleItemIndex
            val size = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
            val offset = index * (size) + listState.firstVisibleItemScrollOffset
            index to offset
        }.distinctUntilChanged()
            .filter { it.first == 0 }
            .map { it.second }
            .collectLatest { offset ->
                val targetScrollOffset = with(density) { offset.toDp() }
                scrollOffset.value = targetScrollOffset
                onScroll(scrollOffset.value, minimumHeaderHeight)
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MaterialTheme.colorScheme.secondary, Color.White),
                ),
            ),
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .zIndex(1000f),
        ) {
            headerContent(
                minimumHeaderHeight,
                maximumHeaderHeight,
                headerHeight,
            )
        }

        // Box to draw background, even if content is empty / not filling screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = max(
                        0.dp,
                        (maximumHeaderHeight + animatedArcHeight - scrollOffset.value),
                    ),
                )
                .background(MaterialTheme.colorScheme.background),
        ) {}

        // whole scroll view which is all over the screen
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
        ) {
            items(
                count = allItems.count(),
                key = bgKey,
                contentType = bgContentType,
                itemContent = bgItemContent,
            )
        }
    }
}

@Preview
@Composable
fun BackgroundViewPreview() {
    val items = listOf("Hello World", "Test", "Next").toImmutableList()

    PreviewContainer {
        BackgroundView(
            headerContent = { minHeight, maxHeight, currentHeight ->
                HeaderView(minHeight, maxHeight, currentHeight)
            },
            items = items,
        ) { item ->
            Text(item)
        }
    }
}
