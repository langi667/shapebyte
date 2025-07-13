package de.stefan.lang.shapebyte.features.home.api

import de.stefan.lang.shapebyte.features.workout.api.Workout
import de.stefan.lang.shapebyte.features.workout.api.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry

data class HomeRootViewData(
    val currWorkoutScheduleEntry: WorkoutScheduleEntry? = null,
    val recentHistory: List<WorkoutHistoryEntry> = emptyList(),
    val quickWorkouts: List<Workout> = emptyList(),
)
