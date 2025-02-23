package de.stefan.lang.shapebyte.features.workout.workoutData

import de.stefan.lang.foundationCore.loadstate.LoadState

interface QuickWorkoutsDatasource {
    suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>>
    suspend fun workoutForId(id: Int): LoadState.Result<Workout>
}
