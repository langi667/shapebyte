package de.stefan.lang.shapebyte.android.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.shared.ui.HeaderView
import de.stefan.lang.shapebyte.android.shared.ui.background.BackgroundView
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.max

private const val DUMMY_LIST_COUNT = 100

@Composable
fun HomeRootView(modifier: Modifier = Modifier.fillMaxSize()) = WithTheme { theme ->
    val items = List(DUMMY_LIST_COUNT) { "Item #$it" }.toImmutableList()
    val buildPerformPersistViewDefaultOffset =
        BuildPerformPersistViewSettings.primaryButtonSize + theme.spacing.xs.dp

    val buildPerformPersistViewOffset =
        remember { mutableStateOf(buildPerformPersistViewDefaultOffset) }

    val headerScale = remember { mutableFloatStateOf(1f) }
    val minimumHeaderHeight = remember {
        mutableStateOf(0.dp)
    }

    Box(modifier) {
        BackgroundView(
            modifier = Modifier.fillMaxSize(),
            headerContent = { minHeight, maxHeight, currentHeight ->
                HeaderView(minHeight, maxHeight, currentHeight)
            },
            content = { scrollOffset, minimumHVHeight ->
                minimumHeaderHeight.value = minimumHVHeight

                buildPerformPersistViewOffset.value =
                    buildPerformPersistViewDefaultOffset - scrollOffset

                headerScale.value = headerScale(
                    scrollOffset = scrollOffset,
                    minimumHeaderHeight = minimumHeaderHeight.value,
                )

                buildPerformPersistViewOffset.value -= (scrollOffset * (headerScale.value * 1.5f))
                Column(Modifier.padding(top = theme.dimensions.medium.dp + theme.spacing.small.dp)) {
                    items.forEach { item ->
                        Text(text = item)
                    }
                }
            },
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Spacer in between top and the BuildPerformPersistView
            // determines the BuildPerformPersistView position
            Box(
                Modifier.height(buildPerformPersistViewOffset.value),
            ) { /*NO OP */ }

            BuildPerformPersistView(
                Modifier
                    .graphicsLayer(
                        scaleX = headerScale.value,
                        scaleY = headerScale.value,
                    ),
            )
        }
    }
}

@Suppress("MagicNumber")
private fun headerScale(
    scrollOffset: Dp,
    minimumHeaderHeight: Dp,
): Float {
    val scaleThreshold = minimumHeaderHeight - 8.dp
    val scale = if (scrollOffset < scaleThreshold) {
        return 1f
    } else {
        max(0.6f, (scaleThreshold.value) / scrollOffset.value)
    }

    return scale
}

@Preview
@Composable
fun HomeRootViewPreview() {
    HomeRootView()
}
