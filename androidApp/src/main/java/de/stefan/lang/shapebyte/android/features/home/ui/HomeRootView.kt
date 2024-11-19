package de.stefan.lang.shapebyte.android.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.stefan.lang.shapebyte.android.designsystem.ui.WithTheme
import de.stefan.lang.shapebyte.android.designsystem.ui.components.text.Head3
import de.stefan.lang.shapebyte.android.features.workout.history.ui.WorkoutHistoryEntryView
import de.stefan.lang.shapebyte.android.features.workout.quick.ui.QuickWorkoutsListView
import de.stefan.lang.shapebyte.android.shared.ui.HeaderView
import de.stefan.lang.shapebyte.android.shared.ui.background.BackgroundView
import de.stefan.lang.shapebyte.features.home.ui.HomeRootViewModel
import de.stefan.lang.shapebyte.features.home.ui.HomeRootViewModelViewData
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.getViewModel
import kotlin.math.max

@Composable
fun HomeRootView(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: HomeRootViewModel = getViewModel(),
) {
    LaunchedEffect(key1 = "Update") {
        viewModel.update()
    }

    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    HomeRootView(uiState, modifier)
}

@Composable
fun HomeRootView(
    uiState: UIState,
    modifier: Modifier = Modifier.fillMaxSize(),
) = WithTheme { theme ->
    val buildPerformPersistViewDefaultOffset =
        BuildPerformPersistViewSettings.primaryButtonSize + theme.spacing.xs.dp

    val buildPerformPersistViewOffset =
        remember { mutableStateOf(buildPerformPersistViewDefaultOffset) }

    val headerScale = remember { mutableFloatStateOf(1f) }
    val minimumHeaderHeight = remember {
        mutableStateOf(0.dp)
    }

    Box(modifier) {
        BackgroundView(
            modifier = Modifier.fillMaxSize(),
            headerContent = { minHeight, maxHeight, currentHeight ->
                HeaderView(minHeight, maxHeight, currentHeight)
            },
            content = { scrollOffset, minimumHVHeight ->
                minimumHeaderHeight.value = minimumHVHeight

                buildPerformPersistViewOffset.value =
                    buildPerformPersistViewDefaultOffset - scrollOffset

                headerScale.value = headerScale(
                    scrollOffset = scrollOffset,
                    minimumHeaderHeight = minimumHeaderHeight.value,
                )

                buildPerformPersistViewOffset.value -= (scrollOffset * (headerScale.value * 1.5f))
                MainContentView(uiState)
            },
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            // Spacer in between top and the BuildPerformPersistView
            // determines the BuildPerformPersistView position
            Box(
                Modifier.height(buildPerformPersistViewOffset.value),
            ) { /*NO OP */ }

            BuildPerformPersistView(
                Modifier
                    .graphicsLayer(
                        scaleX = headerScale.value,
                        scaleY = headerScale.value,
                    ),
            )
        }
    }
}

@Composable
private fun MainContentView(uiState: UIState) = WithTheme { theme ->
    val uiStateData: HomeRootViewModelViewData = uiState.dataOrElse { HomeRootViewModelViewData() }

    Column(modifier = Modifier.fillMaxSize()) {
        // Spacing in between the BuildPerformPersistView and the workout data view
        Box(modifier = Modifier.height(theme.spacing.xxLarge.dp + theme.spacing.xs.dp))

        QuickWorkoutSection(uiStateData.quickWorkouts.toImmutableList())
        RecentHistorySection(uiStateData.recentHistory.toImmutableList())
    }
}

@Composable
private fun RecentHistorySection(history: ImmutableList<WorkoutHistoryEntry>) = WithTheme { theme ->
    if (history.isEmpty()) {
        return@WithTheme
    }

    Column {
        Spacer(modifier = Modifier.height(theme.spacing.medium.dp))
        SectionHeader("Recent History")

        for (entry in history) {
            WorkoutHistoryEntryView(
                entry = entry,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = theme.spacing.small.dp)
                    .padding(horizontal = theme.spacing.small.dp),
            )
        }
    }
}

@Composable
private fun QuickWorkoutSection(workouts: ImmutableList<Workout>) = WithTheme { theme ->
    if (workouts.isEmpty()) {
        return@WithTheme
    }

    Column {
        SectionHeader("Quick Workouts")
        Spacer(modifier = Modifier.height(theme.spacing.small.dp))
        QuickWorkoutsListView(
            quickWorkouts = workouts,
        )
    }
}

@Composable
private fun SectionHeader(title: String, modifier: Modifier = Modifier) = WithTheme { theme ->
    Head3(
        title,
        modifier = modifier.padding(horizontal = theme.spacing.small.dp),
    )
}

@Suppress("MagicNumber")
private fun headerScale(
    scrollOffset: Dp,
    minimumHeaderHeight: Dp,
): Float {
    val scaleThreshold = minimumHeaderHeight - 8.dp
    val scale = if (scrollOffset < scaleThreshold) {
        return 1f
    } else {
        max(0.6f, (scaleThreshold.value) / scrollOffset.value)
    }

    return scale
}

@Preview
@Composable
@Suppress("MagicNumber")
fun HomeRootViewPreview() {
    val uiState = UIState.Data(
        HomeRootViewModelViewData(
            quickWorkouts = List(5) {
                Workout(
                    name = "HIIT Workout ${it + 1}",
                    shortDescription = "${20 + it} min. legs, core",
                    image = ImageAsset("sprints.png"),
                )
            },
        ),
    )

    HomeRootView(uiState)
}
