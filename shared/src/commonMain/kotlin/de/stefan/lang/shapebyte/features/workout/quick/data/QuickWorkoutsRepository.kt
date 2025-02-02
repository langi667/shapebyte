package de.stefan.lang.shapebyte.features.workout.quick.data

import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging

// TODO: Test
class QuickWorkoutsRepository(
    private val dataSource: QuickWorkoutsDatasource,
    override val logger: Logging,
) : Loggable {
    // TODO: consider caching
    suspend fun fetchQuickWorkouts() = dataSource.fetchQuickWorkouts()
    suspend fun workoutForId(id: Int) = dataSource.workoutForId(id)
}
