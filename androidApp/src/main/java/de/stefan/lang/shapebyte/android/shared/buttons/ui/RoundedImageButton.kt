package de.stefan.lang.shapebyte.android.shared.buttons.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

@Composable
fun RoundedImageButton(
    @DrawableRes image: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    appearance: RoundedImageButtonAppearance = RoundedImageButtonAppearance.Medium,
    onClick: (() -> Unit)?,
) {
    Image(
        painter = painterResource(image),
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .clip(CircleShape)
            .size(appearance.size)
            .clickable(onClick != null) {
                onClick?.invoke()
            },
    )
}

@Preview
@Composable
fun RoundedImageButtonPreview() {
    PreviewContainer {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            RoundedImageButtonAppearance.entries.forEach {
                Spacer(Modifier.height(20.dp))
                RoundedImageButton(
                    image = R.drawable.logo,
                    contentDescription = "Logo",
                    appearance = it,
                ) { /* No op */ }
            }
        }
    }
}
