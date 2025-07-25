package de.stefan.lang.shapebyte.features.workout.api.schedule

import de.stefan.lang.coreutils.api.progress.Progress
import kotlinx.datetime.Instant

data class WorkoutScheduleEntry(
    val id: String,
    val name: String,
    val date: Instant,
    val progress: Progress,
)
