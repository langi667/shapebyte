package de.stefan.lang.shapebyte.features.workout.history.data

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface WorkoutHistoryDataSource {
    // TODO: Flow of LoadState
    fun historyForDates(date: Instant, pastDate: Instant): Flow<List<WorkoutScheduleEntry>>
}
