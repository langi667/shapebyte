package de.stefan.lang.shapebyte.features.workout.data.contract.quick

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout

public interface QuickWorkoutsDatasource {
    public suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>>
    public suspend fun workoutForId(id: Int): LoadState.Result<Workout>
}
