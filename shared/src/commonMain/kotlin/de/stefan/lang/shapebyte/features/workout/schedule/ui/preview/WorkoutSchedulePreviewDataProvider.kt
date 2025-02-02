package de.stefan.lang.shapebyte.features.workout.schedule.ui.preview

import de.stefan.lang.core.progress.Progress
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import kotlinx.datetime.Instant

@Suppress("MagicNumber")
object WorkoutSchedulePreviewDataProvider {
    val workoutScheduleEntry: WorkoutScheduleEntry = WorkoutScheduleEntry(
        id = "1",
        name = "Legs",
        date = Instant.parse("2024-10-19T00:00:00Z"),
        progress = Progress(0.7f),
    )
}
