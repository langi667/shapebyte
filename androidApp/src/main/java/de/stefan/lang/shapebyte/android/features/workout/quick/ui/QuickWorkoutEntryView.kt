package de.stefan.lang.shapebyte.android.features.workout.quick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.Footnote
import de.stefan.lang.shapebyte.android.shared.ui.image.AsyncImage
import de.stefan.lang.shapebyte.android.utils.assets.assetsPath
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.utils.assets.ImageAsset

@Composable
fun QuickWorkoutEntryView(
    workout: Workout,
    modifier: Modifier = Modifier,
) = WithTheme { theme, _ ->
    val bgShape = MaterialTheme.shapes.large
    val imageSize = theme.dimensions.small.dp + theme.spacing.medium.dp
    val maxViewWidth = theme.dimensions.xLarge.dp
    val itemSpacing = theme.spacing.xxs.dp

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = bgShape,
            )
            .width(maxViewWidth)
            .clip(bgShape),
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.size(theme.spacing.xs.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.size(itemSpacing))
            Footnote(
                text = workout.name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            AsyncImage(
                url = workout.image.assetsPath,
                modifier = Modifier.size(imageSize),
                contentDescription = workout.name,
            )

            Footnote(
                text = workout.shortDescription,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(itemSpacing))
        }

        Spacer(modifier = Modifier.size(theme.spacing.xs.dp))
    }
}

@Preview
@Composable
fun QuickWorkoutEntryViewPreview() {
    Column(modifier = Modifier.background(Color.LightGray)) {
        QuickWorkoutEntryView(
            workout = Workout(
                name = "HIIT Workout",
                shortDescription = "20 min. legs, core",
                image = ImageAsset("sprints.png"),
            ),
        )

        Spacer(modifier = Modifier.size(8.dp))
        QuickWorkoutEntryView(
            workout = Workout(
                name = "very long HIIT Workout title that is too long",
                shortDescription = "20 min. legs, core",
                image = ImageAsset("sprints.png"),
            ),
        )
        Spacer(modifier = Modifier.size(8.dp))
        QuickWorkoutEntryView(
            workout = Workout(
                name = "HIIT Workout",
                shortDescription = "20 min. legs, core 20 min. legs, core 20 min. legs, core",
                image = ImageAsset("sprints.png"),
            ),
        )

        Spacer(modifier = Modifier.size(8.dp))
        QuickWorkoutEntryView(
            workout = Workout(
                name = "very long HIIT Workout title that is too long",
                shortDescription = "20 min. legs, core 20 min. legs, core 20 min. legs, core 20 min. " +
                    "legs, core",
                image = ImageAsset("sprints.png"),
            ),
        )
    }
}
