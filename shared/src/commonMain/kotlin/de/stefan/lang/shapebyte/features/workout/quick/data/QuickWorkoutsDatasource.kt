package de.stefan.lang.shapebyte.features.workout.quick.data

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.shared.loading.data.LoadState

interface QuickWorkoutsDatasource {
    suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>>
    suspend fun workoutForId(id: Int): LoadState.Result<Workout>
}
