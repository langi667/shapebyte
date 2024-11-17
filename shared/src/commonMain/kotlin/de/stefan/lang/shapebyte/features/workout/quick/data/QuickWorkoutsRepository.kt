package de.stefan.lang.shapebyte.features.workout.quick.data

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.flow.Flow
// TODO: Test
class QuickWorkoutsRepository(
    private val quickWorkoutsDataSource: QuickWorkoutsDatasource,
    override val logger: Logging,
) : Loggable {
    fun fetchQuickWorkouts(): Flow<List<Workout>> {
        return quickWorkoutsDataSource.fetchQuickWorkouts()
    }
}
