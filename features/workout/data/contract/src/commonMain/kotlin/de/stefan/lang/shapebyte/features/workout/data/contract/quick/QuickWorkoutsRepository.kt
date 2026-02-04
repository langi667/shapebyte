package de.stefan.lang.shapebyte.features.workout.data.contract.quick

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

// TODO: Test
public class QuickWorkoutsRepository public constructor(
    private val dataSource: QuickWorkoutsDatasource,
    public override val logger: Logger,
) : Loggable {
    // TODO: consider caching
    public suspend fun fetchQuickWorkouts(): LoadState.Result<List<Workout>> = dataSource.fetchQuickWorkouts()
    public suspend fun workoutForId(id: Int): LoadState.Result<Workout> = dataSource.workoutForId(id)
}
