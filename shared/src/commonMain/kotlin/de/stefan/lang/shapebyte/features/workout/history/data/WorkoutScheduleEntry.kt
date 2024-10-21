package de.stefan.lang.shapebyte.features.workout.history.data

import de.stefan.lang.shapebyte.utils.Progress
import kotlinx.datetime.Instant

data class WorkoutScheduleEntry(
    val id: String,
    val name: String,
    val date: Instant,
    val progress: Progress,
)
