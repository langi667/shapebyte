package de.stefan.lang.shapebyte.android.shared.buttons.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

@Composable
fun PauseButton(
    modifier: Modifier = Modifier,
    contentDescription: String = "Pause",
    appearance: RoundedImageButtonAppearance = RoundedImageButtonAppearance.Small,
    onClick: () -> Unit,
) {
    RoundedImageButton(
        image = R.drawable.pause_button,
        contentDescription = contentDescription,
        modifier = modifier,
        appearance = appearance,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun PauseButtonPreview() {
    PreviewContainer {
        PauseButton { /* No Op */ }
    }
}
