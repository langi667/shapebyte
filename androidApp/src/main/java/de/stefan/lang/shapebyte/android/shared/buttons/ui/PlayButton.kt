package de.stefan.lang.shapebyte.android.shared.buttons.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    contentDescription: String = "Play",
    appearance: RoundedImageButtonAppearance = RoundedImageButtonAppearance.Large,
    onClick: (() -> Unit)?,
) {
    RoundedImageButton(
        image = R.drawable.play_button_large,
        contentDescription = contentDescription,
        modifier = modifier,
        appearance = appearance,
        onClick = onClick,
    )
}

@Preview
@Composable
fun PlayButtonPreview() {
    PreviewContainer {
        PlayButton { /* No Op */ }
    }
}
