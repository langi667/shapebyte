package de.stefan.lang.shapebyte.features.workout.data.contract.schedule

import de.stefan.lang.coreutils.contract.progress.Progress
import kotlinx.datetime.Instant

public data class WorkoutScheduleEntry public constructor(
    public val id: String,
    public val name: String,
    public val date: Instant,
    public val progress: Progress,
)
