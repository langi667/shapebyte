package de.stefan.lang.shapebyte.android.shared.ui.background

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Performance issue: Initially the animations are not smooth when scrolling. This is because the
 * LazyColum gets resized while scrolling which causes multiple recompositions and re-emissions of the
 * scroll state.
 *
 * Idea: size the LazyColum to parent view size -minus minimum header. That is the maximum height.
 * Maybe also use offset and unwrap bottom settings
 */

@Composable
fun <T> BackgroundViewWithHeaderAndList(
    items: ImmutableList<T>,
    modifier: Modifier = Modifier,
    key: ((item: T) -> Any)? = null,
    contentType: (item: T) -> Any? = { null },
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        val arcOffset = remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        val animatedArcOffset by animateDpAsState(targetValue = arcOffset.value, label = "animatedArcOffset")

        BackgroundViewWithHeader(
            radialOffset = animatedArcOffset,
            modifier = Modifier.fillMaxSize(),
        ) {
            LaunchedEffect(listState) {
                coroutineScope.launch {
                    snapshotFlow {
                        listState.firstVisibleItemIndex *
                            (listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0) +
                            listState.firstVisibleItemScrollOffset
                    }
                        .distinctUntilChanged()
                        .collectLatest { offset ->
                            arcOffset.value = with(density) { offset.toDp() }
                        }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    ),
                state = listState,
            ) {
                items(
                    items = items,
                    key = key,
                    contentType = contentType,
                    itemContent = itemContent,
                )
            }
        }
    }
}

@Preview
@Composable
fun BackgroundViewWithHeaderAndListPreview() {
    BackgroundViewWithHeader {
        Text("Hello World")
    }
}
