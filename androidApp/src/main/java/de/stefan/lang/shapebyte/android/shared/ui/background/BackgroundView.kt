package de.stefan.lang.shapebyte.android.shared.ui.background

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.shared.ui.shapes.Arc

@Composable
@Suppress("MagicNumber")
fun BackgroundView(
    modifier: Modifier = Modifier,
    radialOffset: Dp = 0.dp,
    headerContent: @Composable (
        minHeight: Dp,
        maxHeight: Dp,
        currentHeight: Dp,
    ) -> Unit = { _, _, _ -> },
    content: @Composable () -> Unit = {},
) {
    WithTheme { theme ->
        val defaultArcHeight = remember { theme.spacing.xxxLarge.dp }
        val minimumHeaderHeight = remember { defaultArcHeight.value.dp / 2 }

        val arcHeight = remember { mutableStateOf(defaultArcHeight) }
        val viewWidth = remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        val arcWidthMultiplier = 1.5f

        Box(
            modifier = modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    val widthInPixels = it.size.width
                    val widthInDp: Dp = with(density) { widthInPixels.toDp() }
                    viewWidth.value = widthInDp
                }
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(MaterialTheme.colorScheme.secondary, Color.White),
                    ),
                ),
        ) {
            val width = viewWidth.value * arcWidthMultiplier
            val animatedArcHeight by animateDpAsState(
                targetValue = arcHeight.value - radialOffset,
                label = "arcHeight",
            )

            val headerHeight = max(minimumHeaderHeight, defaultArcHeight.value.dp - radialOffset)
            val defaultOffsetY = theme.spacing.small.dp
            val offsetY = remember { mutableStateOf(defaultOffsetY) }

            offsetY.value = if (headerHeight == minimumHeaderHeight) {
                defaultOffsetY - min(defaultOffsetY, radialOffset - minimumHeaderHeight)
            } else {
                defaultOffsetY
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight),
            ) {
                headerContent(
                    minimumHeaderHeight,
                    defaultArcHeight,
                    animatedArcHeight,
                )
            }

            Arc(
                Modifier.width(width)
                    .padding(top = headerHeight + offsetY.value)
                    .wrapContentWidth(unbounded = true),
                height = animatedArcHeight,
                width = width,
                color = MaterialTheme.colorScheme.background,
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(top = max(minimumHeaderHeight, headerHeight + animatedArcHeight / 2) + offsetY.value)
                    .background(Color.Cyan),
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    BackgroundView()
}
