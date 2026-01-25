package de.stefan.lang.shapebyte.features.workout.workoutData.workout

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.Workout

interface QuickWorkoutsDatasource {
    suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>>
    suspend fun workoutForId(id: Int): LoadState.Result<Workout>
}
