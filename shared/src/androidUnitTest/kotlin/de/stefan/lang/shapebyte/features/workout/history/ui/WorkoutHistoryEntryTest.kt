package de.stefan.lang.shapebyte.features.workout.history.ui

import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.progress.Progress
import de.stefan.lang.foundationCore.stringformatter.DateTimeStringFormatter
import de.stefan.lang.shapebyte.features.BaseWorkoutFeatureTest
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkoutHistoryEntryTest : BaseWorkoutFeatureTest() {
    @Test
    fun `forwarded entries from WorkoutScheduleEntry should be correct `() {
        val entry = WorkoutScheduleEntry(
            id = "id",
            name = "name",
            date = Instant.parse("2024-12-24T00:00:00Z"),
            progress = Progress.COMPLETE,
        )

        val result = WorkoutHistoryEntry(
            entry = entry,
            dateStringFormatter = DateTimeStringFormatter(),
        )

        assertEquals(result.entry, entry)
        assertEquals(result.id, entry.id)
        assertEquals(result.name, entry.name)
        assertEquals(result.dateString, "24.12.2024")
    }
}
