package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.shapebyte.features.workout.workout.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.Workout

data class HomeRootViewData(
    val currWorkoutScheduleEntry: WorkoutScheduleEntry? = null,
    val recentHistory: List<WorkoutHistoryEntry> = emptyList(),
    val quickWorkouts: List<Workout> = emptyList(),
)
