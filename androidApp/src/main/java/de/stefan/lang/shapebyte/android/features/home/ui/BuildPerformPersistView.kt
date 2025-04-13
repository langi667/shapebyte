package de.stefan.lang.shapebyte.android.features.home.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import de.stefan.lang.shapebyte.android.R
import de.stefan.lang.shapebyte.android.designsystem.ui.ThemeData
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer

private const val LARGE_Z_INDEX = 1000f

object BuildPerformPersistViewSettings {
    val primaryButtonSize = ThemeData.dimensions.large.dp
    val secondaryButtonSize = ThemeData.dimensions.medium.dp
    val secondaryButtonOffset = ThemeData.dimensions.xTiny.dp
}

@Composable
fun BuildPerformPersistView(
    modifier: Modifier = Modifier,
) {
    val primaryButtonSize = BuildPerformPersistViewSettings.primaryButtonSize
    val secondaryButtonSize = BuildPerformPersistViewSettings.secondaryButtonSize
    val secondaryButtonOffset = BuildPerformPersistViewSettings.secondaryButtonOffset

    val width = primaryButtonSize + (secondaryButtonSize * 2) - (secondaryButtonOffset * 2)

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Box(
            modifier = Modifier
                .size(ThemeData.dimensions.large.dp)
                .clipToBounds()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .wrapContentSize(Alignment.Center)
                .zIndex(LARGE_Z_INDEX),
        ) {
            ImgButton(
                imageRes = R.drawable.logo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ThemeData.spacings.tiny.dp),
                onClick = { /*TODO*/ },
            )
        }

        Row(
            modifier = Modifier.width(width),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ImgButton(
                imageRes = R.drawable.plan,
                modifier = Modifier.size(secondaryButtonSize),
                onClick = { /*TODO*/ },
            )

            ImgButton(
                imageRes = R.drawable.persist,
                modifier = Modifier.size(secondaryButtonSize),
                onClick = { /*TODO*/ },
            )
        }
    }
}

@Composable
private fun ImgButton(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier.clipToBounds(), contentAlignment = Alignment.Center) {
        Button(
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(0.dp),
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Perform",
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview
@Composable
fun BuildPerformPersistViewPreview() {
    PreviewContainer {
        BuildPerformPersistView(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}
