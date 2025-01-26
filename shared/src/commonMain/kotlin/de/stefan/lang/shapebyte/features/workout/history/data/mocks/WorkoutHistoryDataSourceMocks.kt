package de.stefan.lang.shapebyte.features.workout.history.data.mocks

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.Progress
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

object WorkoutHistoryDataSourceMocks : WorkoutHistoryDataSource {
    override suspend fun historyForDates(
        date: Instant,
        pastDate: Instant,
    ): LoadState.Result<List<WorkoutScheduleEntry>> {
        val history = createHistoryEntries(date, pastDate)
        return LoadState.Success(history)
    }

    fun createHistoryEntries(date: Instant, pastDate: Instant): List<WorkoutScheduleEntry> {
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
