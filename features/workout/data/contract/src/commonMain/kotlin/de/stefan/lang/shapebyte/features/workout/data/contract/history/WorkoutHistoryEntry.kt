package de.stefan.lang.shapebyte.features.workout.data.contract.history

import de.stefan.lang.foundation.core.contract.image.ImageResource
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry

public data class WorkoutHistoryEntry public constructor(
    public val entry: WorkoutScheduleEntry,
    private val dateStringFormatter: DateTimeStringFormatter,
) {
    public val id: String by lazy { entry.id }
    public val name: String by lazy { entry.name }

    public val dateString: String by lazy {
        dateStringFormatter.formatDateToDDMMYYYY(entry.date)
    }

    // TODO: receive from WorkoutScheduleEntry / Workout
    public val image: ImageResource by lazy { ImageResource(id = "Sprints.png") }
}
