package de.stefan.lang.shapebyte.android.shared.background.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.BodyMedium
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer
import de.stefan.lang.shapebyte.android.shared.shapes.ui.Arc

@Composable
fun RadialBackgroundView(
    topView: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    radialBackground: Color? = null,
    contentView: @Composable () -> Unit,
) = With { theme ->
    val radialBG = radialBackground ?: theme.current.colorScheme.background

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(theme.current.colorScheme.secondary),
    ) {
        topView()
        Arc(
            Modifier
                .fillMaxWidth()
                .height((theme.dimensions.medium - theme.dimensions.xTiny).dp),
            color = radialBG,
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(radialBG),
        ) {
            contentView()
        }
    }
}

@Composable
@Preview
fun PreviewRadialBackgroundView() {
    PreviewContainer {
        RadialBackgroundView(
            topView = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(129.dp),
                )
            },
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                BodyMedium("Hello World")
            }
        }
    }
}

@Composable
@Preview
fun PreviewRadialBackgroundView_otherColor() {
    PreviewContainer {
        RadialBackgroundView(
            radialBackground = Color.Red,
            topView = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(129.dp),
                )
            },
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                BodyMedium("Hello World")
            }
        }
    }
}
