package de.stefan.lang.shapebyte.android.features.home.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.features.workout.history.ui.WorkoutHistoryEntryView
import de.stefan.lang.shapebyte.android.features.workout.quick.ui.QuickWorkoutsListView
import de.stefan.lang.shapebyte.android.shared.ui.content.ui.ContentView
import de.stefan.lang.shapebyte.android.shared.ui.preview.PreviewContainer
import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.features.home.ui.HomeRootViewModel
import de.stefan.lang.shapebyte.features.home.ui.HomeRootViewModelViewData
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.assets.ImageAsset
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.getViewModel
import kotlin.math.max

@Composable
fun HomeRootView(
    modifier: Modifier = Modifier,
    viewModel: HomeRootViewModel = getViewModel(),
) {
    LaunchedEffect(key1 = "Update") {
        viewModel.update()
    }

    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    HomeRootView(uiState, modifier.fillMaxSize())
}

@Composable
fun HomeRootView(
    uiState: UIState,
    modifier: Modifier = Modifier.fillMaxSize(),
) = With { _, spacings, _ ->
    val buildPerformPersistViewDefaultOffset =
        BuildPerformPersistViewSettings.primaryButtonSize + spacings.tiny.dp

    val buildPerformPersistViewOffset =
        remember { mutableStateOf(buildPerformPersistViewDefaultOffset) }

    val headerScale = remember { mutableFloatStateOf(1f) }
    val minimumHeaderHeight = remember {
        mutableStateOf(0.dp)
    }

    Box(modifier) {
        val uiStateData: HomeRootViewModelViewData =
            uiState.dataOrElse { HomeRootViewModelViewData() }

        ContentView(
            modifier = Modifier.fillMaxSize(),
            onScroll = { scrollOffset, minimumHVHeight ->
                minimumHeaderHeight.value = minimumHVHeight

                buildPerformPersistViewOffset.value =
                    buildPerformPersistViewDefaultOffset - scrollOffset

                headerScale.floatValue = headerScale(
                    scrollOffset = scrollOffset,
                    minimumHeaderHeight = minimumHeaderHeight.value,
                )

                buildPerformPersistViewOffset.value -= (scrollOffset * (headerScale.floatValue * 1.5f))
            },
        ) {
            spacer(spacings.xxLarge.dp + spacings.small.dp)

            val quickWorkouts = uiStateData.quickWorkouts

            if (quickWorkouts.isNotEmpty()) {
                sectionTitle("Quick Workouts")
                spacer(spacings.small.dp)

                data(
                    id = FeatureId.QUICK_WORKOUTS.name,
                    data = quickWorkouts,
                    stableKey = FeatureId.QUICK_WORKOUTS.name,
                ) {
                    QuickWorkoutsListView(
                        quickWorkouts = quickWorkouts.toImmutableList(),
                    )
                }

                spacer(spacings.medium.dp)
            }

            val recentHistory = uiStateData.recentHistory
            if (recentHistory.isNotEmpty()) {
                sectionTitle("Recent History")

                for (entry in recentHistory) {
                    data(
                        id = FeatureId.RECENT_HISTORY.name,
                        data = recentHistory,
                    ) {
                        WorkoutHistoryEntryView(
                            entry = entry,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = spacings.small.dp)
                                .padding(horizontal = spacings.small.dp),
                        )
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val buildPerformPersistViewOffsetAnimated by animateDpAsState(
                targetValue = buildPerformPersistViewOffset.value,
                label = "scrollOffset",
            )

            // Spacer in between top and the BuildPerformPersistView
            // determines the BuildPerformPersistView position
            Box(
                Modifier.height(buildPerformPersistViewOffsetAnimated),
            ) { /*NO OP */ }

            BuildPerformPersistView(
                Modifier
                    .graphicsLayer(
                        scaleX = headerScale.floatValue,
                        scaleY = headerScale.floatValue,
                    ),
            )
        }
    }
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

    PreviewContainer {
        HomeRootView(uiState)
    }
}
