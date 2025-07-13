package de.stefan.lang.shapebyte.features.workout.workoutData.workout

import de.stefan.lang.coreutils.api.logging.Loggable
import de.stefan.lang.coreutils.api.logging.Logging

// TODO: Test
class QuickWorkoutsRepository(
    private val dataSource: QuickWorkoutsDatasource,
    override val logger: Logging,
) : Loggable {
    // TODO: consider caching
    suspend fun fetchQuickWorkouts() = dataSource.fetchQuickWorkouts()
    suspend fun workoutForId(id: Int) = dataSource.workoutForId(id)
}
