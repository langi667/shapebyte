package de.stefan.lang.shapebyte.android.features.workout.quick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.LabelMedium
import de.stefan.lang.shapebyte.android.shared.image.ui.AsyncImage
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.core.data.WorkoutType
import de.stefan.lang.shapebyte.utils.assets.ImageAsset

@Composable
fun QuickWorkoutEntryView(
    workout: Workout,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) = With { dimensions, spacings, logger ->
    val bgShape = MaterialTheme.shapes.large
    val imageSize = dimensions.small.dp + spacings.medium.dp
    val maxViewWidth = dimensions.xLarge.dp
    val itemSpacing = spacings.xTiny.dp

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = bgShape,
            )
            .width(maxViewWidth)
            .clip(bgShape).clickable(true) {
                logger.d("QuickWorkoutEntryView", "workout clicked: $workout")
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.size(spacings.tiny.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.size(itemSpacing))
            LabelMedium(
                text = workout.name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            AsyncImage(
                asset = workout.image,
                modifier = Modifier.size(imageSize),
                contentDescription = workout.name,
            )

            LabelMedium(
                text = workout.shortDescription,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(itemSpacing))
        }

        Spacer(modifier = Modifier.size(spacings.tiny.dp))
    }
}

@Preview
@Composable
fun QuickWorkoutEntryViewPreview() {
    PreviewContainer {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            QuickWorkoutEntryView(
                workout = Workout(
                    name = "HIIT Workout",
                    shortDescription = "20 min. legs, core",
                    image = ImageAsset("sprints.png"),
                    id = 1,
                    type = WorkoutType.Timed.Interval(0, 0, 0),
                ),
            )

            Spacer(modifier = Modifier.size(8.dp))
            QuickWorkoutEntryView(
                workout = Workout(
                    name = "very long HIIT Workout title that is too long",
                    shortDescription = "20 min. legs, core",
                    image = ImageAsset("sprints.png"),
                    id = 2,
                    type = WorkoutType.Timed.Interval(0, 0, 0),
                ),
            )
            Spacer(modifier = Modifier.size(8.dp))
            QuickWorkoutEntryView(
                workout = Workout(
                    name = "HIIT Workout",
                    shortDescription = "20 min. legs, core 20 min. legs, core 20 min. legs, core",
                    image = ImageAsset("sprints.png"),
                    id = 3,
                    type = WorkoutType.Timed.Interval(0, 0, 0),
                ),
            )

            Spacer(modifier = Modifier.size(8.dp))
            QuickWorkoutEntryView(
                workout = Workout(
                    name = "very long HIIT Workout title that is too long",
                    shortDescription = "20 min. legs, core 20 min. legs, core 20 min. legs, core 20 min. " +
                        "legs, core",
                    image = ImageAsset("sprints.png"),
                    id = 4,
                    type = WorkoutType.Timed.Interval(0, 0, 0),
                ),
            )
        }
    }
}
