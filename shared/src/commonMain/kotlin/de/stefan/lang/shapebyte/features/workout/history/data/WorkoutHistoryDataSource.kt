package de.stefan.lang.shapebyte.features.workout.history.data

import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import kotlinx.datetime.Instant

interface WorkoutHistoryDataSource {
    suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>>
}
