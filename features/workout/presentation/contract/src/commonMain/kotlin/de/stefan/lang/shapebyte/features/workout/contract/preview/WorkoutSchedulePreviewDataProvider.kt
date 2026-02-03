package de.stefan.lang.shapebyte.features.workout.contract.preview

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import kotlinx.datetime.Instant

@Suppress("MagicNumber")
public object WorkoutSchedulePreviewDataProvider {
    public val workoutScheduleEntry: WorkoutScheduleEntry = WorkoutScheduleEntry(
        id = "1",
        name = "Legs",
        date = Instant.parse("2024-10-19T00:00:00Z"),
        progress = Progress(0.7f),
    )
}
