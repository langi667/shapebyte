package de.stefan.lang.shapebyte.features.workout.data.contract.quick

import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging

// TODO: Test
class QuickWorkoutsRepository(
    private val dataSource: QuickWorkoutsDatasource,
    override val logger: Logging,
) : Loggable {
    // TODO: consider caching
    suspend fun fetchQuickWorkouts() = dataSource.fetchQuickWorkouts()
    suspend fun workoutForId(id: Int) = dataSource.workoutForId(id)
}
