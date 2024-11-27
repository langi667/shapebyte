package de.stefan.lang.shapebyte.android.designsystem.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import de.stefan.lang.shapebyte.android.shared.ui.preview.PreviewContainer

@Composable
fun BodyMedium(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
    )
}

@Preview
@Composable
fun BodyBodyMediumPreview() {
    PreviewContainer {
        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
        ) {
            BodyMedium(text = "Body")
        }
    }
}
