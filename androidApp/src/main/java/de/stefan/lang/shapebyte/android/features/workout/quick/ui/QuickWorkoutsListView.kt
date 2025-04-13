package de.stefan.lang.shapebyte.android.features.workout.quick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.stefan.lang.foundationCore.image.ImageResource
import de.stefan.lang.shapebyte.android.designsystem.ui.ThemeData
import de.stefan.lang.shapebyte.android.shared.preview.ui.PreviewContainer
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutType
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.Workout
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun QuickWorkoutsListView(
    quickWorkouts: ImmutableList<Workout>,
    modifier: Modifier = Modifier,
    onSelectWorkout: ((Workout) -> Unit) = {},
) {
    val paddingHorizontal = ThemeData.spacings.small.dp
    val spacerHeight = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val horizontalClipWidth = paddingHorizontal * 2

    Box(modifier = modifier.fillMaxHeight()) {
        val listState = rememberLazyListState()
        val snapFlingBehavior = rememberSnapFlingBehavior(listState)

        LazyRow(
            state = listState,
            flingBehavior = snapFlingBehavior,
        ) {
            items(quickWorkouts) { currItem ->
                val paddingEnd = if (currItem == quickWorkouts.last()) paddingHorizontal else 0.dp
                QuickWorkoutEntryView(
                    workout = currItem,
                    modifier = Modifier
                        .padding(start = paddingHorizontal)
                        .padding(end = paddingEnd)
                        .onGloballyPositioned {
                            val heightDP = with(density) { it.size.height.toDp() }
                            spacerHeight.value = heightDP
                        },
                    onClick = { onSelectWorkout(currItem) },
                )
            }
        }

        HorizontalClip(
            width = horizontalClipWidth,
            height = spacerHeight.value,
            color = MaterialTheme.colorScheme.background,
        )

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
        ) {
            HorizontalClip(
                width = horizontalClipWidth,
                height = spacerHeight.value,
                color = MaterialTheme.colorScheme.background,
                invert = true,
            )
        }
    }
}

@Composable
private fun HorizontalClip(
    width: Dp,
    height: Dp,
    color: Color,
    invert: Boolean = false,
) {
    val gradientStart: Offset = if (invert) Offset(Float.POSITIVE_INFINITY, 0f) else Offset.Zero
    val gradientEnd: Offset = if (invert) Offset.Zero else Offset(Float.POSITIVE_INFINITY, 0f)

    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(color, Color.Transparent),
                    start = gradientStart,
                    end = gradientEnd,
                ),
            ),

    )
}

@Preview
@Composable
@Suppress("MagicNumber")
fun QuickWorkoutsListViewPreview() {
    val workouts = List(6) {
        Workout(
            name = "Workout ${it + 1}",
            shortDescription = "Short description ${it + 1}",
            image = ImageResource("sprints.png"),
            id = 1,
            type = WorkoutType.Timed.Interval(0, 0, 0),
        )
    }.toImmutableList()

    PreviewContainer {
        QuickWorkoutsListView(
            quickWorkouts = workouts,
        )
    }
}
