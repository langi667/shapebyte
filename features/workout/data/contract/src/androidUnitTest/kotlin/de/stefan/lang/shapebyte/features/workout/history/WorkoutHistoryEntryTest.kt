package de.stefan.lang.shapebyte.features.workout.history

import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.foundation.core.contract.FoundationCoreContract
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.features.workout.BaseTest
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkoutHistoryEntryTest: BaseTest()  {
    @Test
    fun `forwarded entries from WorkoutScheduleEntry should be correct `() {
        val entry = WorkoutScheduleEntry(
            id = "id",
            name = "name",
            date = Instant.Companion.parse("2024-12-24T00:00:00Z"),
            progress = Progress.Companion.COMPLETE,
        )

        val result = WorkoutHistoryEntry(
            entry = entry,
            dateStringFormatter = FoundationCoreModule.dateTimeStringFormatter(),
        )

        assertEquals(result.entry, entry)
        assertEquals(result.id, entry.id)
        assertEquals(result.name, entry.name)
        assertEquals(result.dateString, "24.12.2024")
    }
}