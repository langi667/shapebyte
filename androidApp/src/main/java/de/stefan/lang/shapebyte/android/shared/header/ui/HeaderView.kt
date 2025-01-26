package de.stefan.lang.shapebyte.android.shared.header.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.BodyMedium
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.HeadlineMedium
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer
import kotlin.math.max
import kotlin.math.min

@Composable
fun HeaderView(
    minHeight: Dp,
    maxHeight: Dp,
    currentHeight: Dp,
    modifier: Modifier = Modifier,
) = With { theme ->
    val scaleDivider =
        if (maxHeight.value == minHeight.value) 1f else (maxHeight.value - minHeight.value)
    val scaleRaw = (currentHeight.value - minHeight.value) / scaleDivider
    val scale = max(0f, scaleRaw)

    theme.logger.d(
        tag = "HeaderView",
        message = "minHeight: $minHeight, maxHeight: $maxHeight, currentHeight: $currentHeight, " +
            "scale: $scale",
    )

    val headerProgress = 1f - min(max(scale, 0f), 1f)
    val contentPadding = theme.spacings.small.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                theme.current.colorScheme.secondary
                    .copy(alpha = headerProgress),
            ),
    )

    Row(
        modifier = Modifier
            .padding(contentPadding),
    ) {
        Column(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    transformOrigin = TransformOrigin(0f, 0f),
                ),
        ) {
            BodyMedium(text = "Welcome back")
            HeadlineMedium(text = "Stefan")
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Description of the image",
        )
    }
}

@Preview
@Composable
fun HeaderViewPreview() {
    PreviewContainer { theme ->
        Box(
            modifier = Modifier.background(theme.current.colorScheme.secondary),
        ) {
            HeaderView(32.dp, 64.dp, 64.dp)
        }
    }
}
