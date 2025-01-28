package de.stefan.lang.shapebyte.features.workout.history.ui

import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import de.stefan.lang.shapebyte.utils.image.ImageResource

data class WorkoutHistoryEntry(
    val entry: WorkoutScheduleEntry,
    private val dateStringFormatter: DateTimeStringFormatter,
) {
    val id: String by lazy { entry.id }
    val name: String by lazy { entry.name }

    val dateString: String by lazy {
        dateStringFormatter.formatDateToDDMMYYYY(entry.date)
    }

    // TODO: receive from WorkoutScheduleEntry / Workout
    val image: ImageResource by lazy { ImageResource(id = "sprints.png") }
}
