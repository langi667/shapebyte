package de.stefan.lang.shapebyte.features.workout.schedule.data

import de.stefan.lang.core.progress.Progress
import kotlinx.datetime.Instant

data class WorkoutScheduleEntry(
    val id: String,
    val name: String,
    val date: Instant,
    val progress: Progress,
)
