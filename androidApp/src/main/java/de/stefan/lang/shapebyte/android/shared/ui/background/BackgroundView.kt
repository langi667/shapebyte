package de.stefan.lang.shapebyte.android.shared.ui.background

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.zIndex
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.shared.ui.HeaderView
import de.stefan.lang.shapebyte.android.shared.ui.shapes.Arc
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
@Suppress("MagicNumber")
fun BackgroundView(
    modifier: Modifier = Modifier,
    headerContent: @Composable (
        minHeight: Dp,
        maxHeight: Dp,
        currentHeight: Dp,
    ) -> Unit = { _, _, _ -> },
    content: @Composable (scrollOffset: Dp, minimumHeaderHeight: Dp) -> Unit = { _, _ -> },
) {
    WithTheme { theme ->
        val defaultArcHeight = remember { theme.dimensions.small.dp * 1.5f }
        val arcHeight = remember { mutableStateOf(defaultArcHeight) }

        val viewSize = remember { mutableStateOf(DpSize.Zero) }
        val density = LocalDensity.current

        val maximumHeaderHeight = theme.dimensions.large.dp
        val minimumHeaderHeight = maximumHeaderHeight / 2

        val scrollOffset = remember { mutableStateOf(0.dp) }

        val animatedArcHeight by animateDpAsState(
            targetValue = arcHeight.value - scrollOffset.value,
            label = "arcHeight",
        )

        val headerHeight =
            max(minimumHeaderHeight, maximumHeaderHeight - (scrollOffset.value * 1.5f))
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(listState) {
            coroutineScope.launch {
                snapshotFlow {
                    listState.firstVisibleItemIndex *
                        (listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0) +
                        listState.firstVisibleItemScrollOffset
                }
                    .distinctUntilChanged()
                    .collectLatest { offset ->
                        scrollOffset.value = with(density) { offset.toDp() }
                        Log.d("BackgroundView", "scrollOffset: $scrollOffset")
                    }
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    val widthDP = with(density) { it.size.width.toDp() }
                    val heightDP = with(density) { it.size.height.toDp() }

                    viewSize.value = DpSize(widthDP, heightDP)
                }
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

            // Box to draw background, even if content is empty/ not filling screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = maximumHeaderHeight + defaultArcHeight)
                    .background(MaterialTheme.colorScheme.background),
            ) {}

            // whole scroll view which is all over the screen
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
            ) {
                items(1) {
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

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background),
                    ) {
                        content(scrollOffset.value, minimumHeaderHeight)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BackgroundView2Preview() {
    BackgroundView(
        headerContent = { minHeight, maxHeight, currentHeight ->
            HeaderView(minHeight, maxHeight, currentHeight)
        },
        content = { _, _ ->
            Text("Hello World")
        },
    )
}
