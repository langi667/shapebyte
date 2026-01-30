package de.stefan.lang.shapebyte.features.workout.data.contract.quick

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout

interface QuickWorkoutsDatasource {
    suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>>
    suspend fun workoutForId(id: Int): LoadState.Result<Workout>
}
