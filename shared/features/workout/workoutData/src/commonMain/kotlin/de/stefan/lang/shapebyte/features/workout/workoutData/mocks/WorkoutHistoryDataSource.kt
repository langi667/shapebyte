package de.stefan.lang.shapebyte.features.workout.workoutData.mocks

import de.stefan.lang.foundationCore.api.loadstate.LoadState
import kotlinx.datetime.Instant

interface WorkoutHistoryDataSource {
    suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>>
}
