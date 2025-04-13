package de.stefan.lang.shapebyte.android.designsystem.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

@Composable
fun TitleLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    fontWeight: FontWeight? = null,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) = MaterialTheme {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = fontWeight,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Preview
@Composable
fun TitleLargePreview() {
    PreviewContainer { theme ->
        Box(
            Modifier
                .fillMaxWidth()
                .background(theme.current.colorScheme.primary),
        ) {
            TitleLarge(text = "TitleLarge")
        }
    }
}
