package de.stefan.lang.shapebyte.features.workout.workoutData.mocks

import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry
import kotlinx.datetime.Instant

interface WorkoutHistoryDataSource {
    suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>>
}
