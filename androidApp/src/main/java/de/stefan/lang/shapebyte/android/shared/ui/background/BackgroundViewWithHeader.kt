package de.stefan.lang.shapebyte.android.shared.ui.background

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.shared.ui.HeaderView

@Composable
fun BackgroundViewWithHeader(
    modifier: Modifier = Modifier,
    radialOffset: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    BackgroundView(
        radialOffset = radialOffset,
        modifier = modifier,
        headerContent = { minHeight, maxHeight, currentHeight ->
            HeaderView(
                minHeight = minHeight,
                maxHeight = maxHeight,
                currentHeight = currentHeight,
            )
        },
    ) {
        content()
    }
}

@Preview
@Composable
fun BackgroundViewWithHeaderPreview() {
    BackgroundViewWithHeader {
        Text("Hello World")
    }
}
