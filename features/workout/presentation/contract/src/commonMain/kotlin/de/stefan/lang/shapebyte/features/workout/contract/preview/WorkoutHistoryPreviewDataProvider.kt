package de.stefan.lang.shapebyte.features.workout.contract.preview

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

public class WorkoutHistoryPreviewDataProvider(
    private val workoutHistoryMapper: (WorkoutScheduleEntry) -> WorkoutHistoryEntry
) {
    public val previewData: List<WorkoutHistoryEntry> by lazy {
        createHistoryEntries(
            Instant.parse("2024-12-24T00:00:00Z"),
            Instant.parse("2024-12-12T00:00:00Z"),
        ).map {
            workoutHistoryMapper(it)
        }
    }

    private fun createHistoryEntries(date: Instant, pastDate: Instant): List<WorkoutScheduleEntry> {
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
