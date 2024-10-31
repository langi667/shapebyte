package de.stefan.lang.shapebyte.android.shared.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.ApplicationTheme
import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.Body
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.Head2
import kotlin.math.max
import kotlin.math.min

@Composable
fun HeaderView(
    minHeight: Dp,
    maxHeight: Dp,
    currentHeight: Dp,
    modifier: Modifier = Modifier,
) {
    val scaleDivider = if (maxHeight.value == minHeight.value) 1f else (maxHeight.value - minHeight.value)
    val scaleRaw = (currentHeight.value - minHeight.value) / scaleDivider
    val scale = max(0f, scaleRaw)

    val headerProgress = 1f - min(max(scale, 0f), 1f)

    WithTheme { theme ->
        val contentPadding = theme.spacing.small.dp

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.secondary
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
                Body(text = "Welcome back")
                Head2(text = "Stefan")
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Description of the image",
            )
        }
    }
}

@Preview
@Composable
fun PreviewHeaderView() {
    ApplicationTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.secondary)) {
            HeaderView(64.dp, 128.dp, 128.dp)
        }
    }
}
