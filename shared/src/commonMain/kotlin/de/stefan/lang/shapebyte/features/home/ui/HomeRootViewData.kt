package de.stefan.lang.shapebyte.features.home.ui

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry

data class HomeRootViewData(
    val currWorkoutScheduleEntry: WorkoutScheduleEntry? = null,
    val recentHistory: List<WorkoutHistoryEntry> = emptyList(),
    val quickWorkouts: List<Workout> = emptyList(),
)
