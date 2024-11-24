package de.stefan.lang.shapebyte.features.workout.history.data.mocks

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.utils.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

object WorkoutHistoryDataSourceMocks : WorkoutHistoryDataSource {
    override fun historyForDates(date: Instant, pastDate: Instant): Flow<List<WorkoutScheduleEntry>> {
        val history = createHistoryEntries(date, pastDate)
        return flowOf(history)
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
