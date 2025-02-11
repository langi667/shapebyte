package de.stefan.lang.shapebyte.features.workout.history.data

import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import kotlinx.datetime.Instant

class WorkoutHistoryRepository(
    private val dataSource: WorkoutHistoryDataSource,
) {
    suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>> {
        return dataSource.historyForDates(date, pastDate)
    }
}
