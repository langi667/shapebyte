package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.shapebyte.features.workout.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.Workout
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutScheduleEntry

data class HomeRootViewData(
    val currWorkoutScheduleEntry: WorkoutScheduleEntry? = null,
    val recentHistory: List<WorkoutHistoryEntry> = emptyList(),
    val quickWorkouts: List<Workout> = emptyList(),
)
