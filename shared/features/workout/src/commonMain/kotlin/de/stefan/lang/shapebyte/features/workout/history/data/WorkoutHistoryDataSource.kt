package de.stefan.lang.shapebyte.features.workout.history.data

import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import kotlinx.datetime.Instant

interface WorkoutHistoryDataSource {
    suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>>
}
