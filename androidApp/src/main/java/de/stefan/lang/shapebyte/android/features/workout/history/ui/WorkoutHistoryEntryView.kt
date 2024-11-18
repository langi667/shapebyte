package de.stefan.lang.shapebyte.android.features.workout.history.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.Footnote
import de.stefan.lang.shapebyte.android.utils.assets.ImageAssetLoader
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.utils.assets.ImageAsset

@Composable
fun WorkoutHistoryEntryView(
    entry: WorkoutHistoryEntry,
    modifier: Modifier = Modifier,
) {
    WorkoutHistoryEntryView(
        title = entry.name,
        date = entry.dateString,
        image = entry.image,
        modifier = modifier,
    )
}

@Composable
fun WorkoutHistoryEntryView(
    title: String,
    date: String,
    image: ImageAsset,
    modifier: Modifier = Modifier,
) = WithTheme { theme ->
    val context = LocalContext.current
    val bgShape = MaterialTheme.shapes.extraLarge
    val itemSpacing = theme.spacing.xs.dp
    val imageSize = theme.dimensions.small.dp + theme.spacing.medium.dp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = bgShape,
            )
            .padding(itemSpacing)
            .clip(bgShape),

    ) {
        ImageAssetLoader.loadImage(image, context)?.let { // TODO: fallback image
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(imageSize),
                bitmap = it.asImageBitmap(),
                contentDescription = title,
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = itemSpacing),

        ) {
            Footnote(
                text = title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(theme.spacing.xxs.dp))
            Footnote(
                text = date,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun WorkoutHistoryEntryViewPreview() {
    Column(modifier = Modifier.background(Color.LightGray)) {
        WorkoutHistoryEntryView(
            title = "HIIT Workout",
            date = "12.12.2021",
            image = ImageAsset("sprints.png"),
        )

        Spacer(modifier = Modifier.size(8.dp))

        WorkoutHistoryEntryView(
            title = "HIIT Workout",
            date = "13.12.2021",
            image = ImageAsset("sprints.png"),
        )
    }
}
