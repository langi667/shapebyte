package de.stefan.lang.shapebyte.features.workout.workoutData.workout

import de.stefan.lang.foundationCore.api.loadstate.LoadState

interface QuickWorkoutsDatasource {
    suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>>
    suspend fun workoutForId(id: Int): LoadState.Result<Workout>
}
