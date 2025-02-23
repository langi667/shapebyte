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
import de.stefan.lang.shapebyte.features.workout.preview.QuickWorkoutsPreviewDataProvider
import de.stefan.lang.shapebyte.features.workout.workoutData.Workout

@Composable
fun QuickWorkoutEntryView(
    workout: Workout,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) = With { theme ->
    val bgShape = theme.current.shapes.large
    val imageSize = theme.dimensions.small.dp + theme.spacings.medium.dp
    val maxViewWidth = theme.dimensions.xLarge.dp
    val itemSpacing = theme.spacings.xTiny.dp

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = theme.current.colorScheme.secondary,
                shape = bgShape,
            )
            .width(maxViewWidth)
            .clip(bgShape)
            .clickable(true) {
                theme.logger.d("QuickWorkoutEntryView", "workout clicked: $workout")
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.size(theme.spacings.tiny.dp))
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
                image = workout.image,
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

        Spacer(modifier = Modifier.size(theme.spacings.tiny.dp))
    }
}

@Preview
@Composable
fun QuickWorkoutEntryViewPreview() {
    PreviewContainer { theme ->
        Column(modifier = Modifier.background(theme.current.colorScheme.background)) {
            QuickWorkoutEntryView(
                workout = QuickWorkoutsPreviewDataProvider.hiit,
            )

            Spacer(modifier = Modifier.size(8.dp))
            QuickWorkoutEntryView(
                workout = QuickWorkoutsPreviewDataProvider.hiitLongTitle,
            )
            Spacer(modifier = Modifier.size(8.dp))
            QuickWorkoutEntryView(
                workout = QuickWorkoutsPreviewDataProvider.hiitLongDescription,
            )

            Spacer(modifier = Modifier.size(8.dp))
            QuickWorkoutEntryView(
                workout = QuickWorkoutsPreviewDataProvider.hiitLongTitleAndDesc,
            )
        }
    }
}
