package de.stefan.lang.shapebyte.android.shared.buttons.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

@Composable
fun StopButton(
    modifier: Modifier = Modifier,
    contentDescription: String = "StopButton",
    appearance: RoundedImageButtonAppearance = RoundedImageButtonAppearance.Small,
    onClick: () -> Unit,
) {
    RoundedImageButton(
        image = R.drawable.stop_button,
        contentDescription = contentDescription,
        modifier = modifier,
        appearance = appearance,
        onClick = onClick,
    )
}

@Preview
@Composable
fun StopButtonPreview() {
    PreviewContainer {
        StopButton { /* No Op */ }
    }
}
