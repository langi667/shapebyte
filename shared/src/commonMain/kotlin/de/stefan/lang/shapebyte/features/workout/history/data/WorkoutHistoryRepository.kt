package de.stefan.lang.shapebyte.features.workout.history.data

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

class WorkoutHistoryRepository(
    private val dataSource: WorkoutHistoryDataSource,
) {
    fun historyForDates(date: Instant, pastDate: Instant): Flow<List<WorkoutScheduleEntry>> {
        return dataSource.historyForDates(date, pastDate)
    }
}
