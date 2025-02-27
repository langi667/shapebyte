package de.stefan.lang.shapebyte.features.workout.workoutData.mocks

import de.stefan.lang.coreutils.progress.Progress
import kotlinx.datetime.Instant

data class WorkoutScheduleEntry(
    val id: String,
    val name: String,
    val date: Instant,
    val progress: Progress,
)
