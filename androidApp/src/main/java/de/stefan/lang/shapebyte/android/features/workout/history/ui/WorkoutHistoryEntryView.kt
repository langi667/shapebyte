package de.stefan.lang.shapebyte.android.features.workout.history.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.stefan.lang.foundationCore.image.ImageResource
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.LabelMedium
import de.stefan.lang.shapebyte.android.shared.image.ui.AsyncImage
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer
import de.stefan.lang.shapebyte.features.workout.preview.WorkoutSchedulePreviewDataProvider
import de.stefan.lang.shapebyte.features.workout.workout.WorkoutHistoryEntry

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
    image: ImageResource,
    modifier: Modifier = Modifier,
) = With { theme ->
    val bgShape = theme.current.shapes.extraLarge
    val itemSpacing = theme.spacings.tiny.dp
    val imageSize = theme.dimensions.small.dp + theme.spacings.medium.dp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = theme.current.colorScheme.secondary,
                shape = bgShape,
            )
            .padding(itemSpacing)
            .clip(bgShape),

    ) {
        AsyncImage(
            image = image,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape),
            contentDescription = title,
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = itemSpacing),

        ) {
            LabelMedium(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(theme.spacings.xTiny.dp))
            LabelMedium(
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
    PreviewContainer { theme ->
        Column(
            Modifier.background(theme.current.colorScheme.background),
        ) {
            WorkoutHistoryEntryView(
                entry = SharedModule.workoutHistoryEntry(
                    scheduleEntry = WorkoutSchedulePreviewDataProvider.workoutScheduleEntry,
                ),
            )
        }
    }
}
