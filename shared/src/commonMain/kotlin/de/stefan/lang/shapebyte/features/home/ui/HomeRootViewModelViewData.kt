package de.stefan.lang.shapebyte.features.home.ui

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.quick.ui.QuickWorkoutsUIState

data class HomeRootViewModelViewData(
    val currWorkoutScheduleEntry: WorkoutScheduleEntry? = null,
    val recentHistory: List<WorkoutHistoryEntry> = emptyList(),
    val quickWorkoutsState: QuickWorkoutsUIState = QuickWorkoutsUIState.Hidden,
)
