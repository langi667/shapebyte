package de.stefan.lang.shapebyte.features.workout.data.contract.history

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import kotlin.time.Instant

public interface WorkoutHistoryDataSource {
    public suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>>
}
