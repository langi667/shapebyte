package de.stefan.lang.shapebyte.features.workout.history.ui

import de.stefan.lang.coreutils.progress.Progress
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.utils.BaseTest
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkoutHistoryEntryTest : BaseTest() {
    @Test
    fun `forwarded entries from WorkoutScheduleEntry should be correct `() {
        val entry = WorkoutScheduleEntry(
            id = "id",
            name = "name",
            date = Instant.parse("2024-12-24T00:00:00Z"),
            progress = Progress.COMPLETE,
        )

        val result = WorkoutModule.workoutHistoryEntry(entry)

        assertEquals(result.entry, entry)
        assertEquals(result.id, entry.id)
        assertEquals(result.name, entry.name)
        assertEquals(result.dateString, "24.12.2024")
    }
}
