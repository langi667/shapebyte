package de.stefan.lang.shapebyte.features.workout.history.data

import de.stefan.lang.shapebyte.shared.loading.data.LoadState
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
