package de.stefan.lang.shapebyte.features.workout.data.fixture

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

public object WorkoutHistoryDataSourceFixture : WorkoutHistoryDataSource {
    public override suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>> {
        val history = createHistoryEntries(date, pastDate)
        return LoadState.Success(history)
    }

    public fun createHistoryEntries(date: Instant, pastDate: Instant): List<WorkoutScheduleEntry> {
        val history = mutableListOf<WorkoutScheduleEntry>()
        val daysCount = date.minus(pastDate).inWholeDays

        for (currDayIndex in 0 until daysCount) {
            val entry = WorkoutScheduleEntry(
                id = "$currDayIndex",
                name = "Workout ${currDayIndex + 1}",
                date = date.minus(currDayIndex.days),
                progress = Progress.COMPLETE,
            )
            history.add(entry)
        }

        return history
    }
}
