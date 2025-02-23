package de.stefan.lang.shapebyte.features.workout.history.ui.preview

import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.history.data.mocks.WorkoutHistoryDataSourceMocks
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import kotlinx.datetime.Instant

// TODO: consider using mock data from file
object WorkoutHistoryPreviewDataProvider {
    private val datasource = WorkoutHistoryDataSourceMocks

    val previewData: List<WorkoutHistoryEntry> by lazy {
        datasource.createHistoryEntries(
            Instant.parse("2024-12-24T00:00:00Z"),
            Instant.parse("2024-12-12T00:00:00Z"),
        ).map {
            WorkoutModule.workoutHistoryEntry(it)
        }
    }
}
