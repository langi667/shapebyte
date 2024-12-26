package de.stefan.lang.shapebyte.android.designsystem.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

@Composable
fun DisplayLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    textAlignment: TextAlign? = null,
) = With { theme ->
    Text(
        modifier = modifier,
        text = text,
        style = theme.current.typography.displayLarge,
        color = color,
        textAlign = textAlignment,
    )
}

@Preview
@Composable
fun DisplayLargePreview() {
    PreviewContainer { theme ->
        Box(
            Modifier
                .fillMaxWidth()
                .background(theme.current.colorScheme.primary),
        ) {
            DisplayLarge(text = "DisplayLarge")
        }
    }
}
