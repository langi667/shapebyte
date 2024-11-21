package de.stefan.lang.shapebyte.android.shared.ui.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun AsyncImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    AsyncImage(
        model = url,
        modifier = modifier,
        contentDescription = contentDescription,
    )
}
