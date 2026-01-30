package de.stefan.lang.shapebyte.features.workout.data.contract.schedule

import de.stefan.lang.coreutils.contract.progress.Progress
import kotlinx.datetime.Instant

data class WorkoutScheduleEntry(
    val id: String,
    val name: String,
    val date: Instant,
    val progress: Progress,
)
