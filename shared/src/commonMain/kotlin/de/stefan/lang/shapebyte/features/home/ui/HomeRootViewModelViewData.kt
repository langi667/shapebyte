package de.stefan.lang.shapebyte.features.home.ui

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry

data class HomeRootViewModelViewData(
    val currWorkoutScheduleEntry: WorkoutScheduleEntry? = null,
    val recentHistory: List<WorkoutHistoryEntry> = emptyList(),
    val quickWorkouts: List<Workout> = emptyList(),
)
