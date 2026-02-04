package de.stefan.lang.shapebyte.features.workout.contract.preview

import de.stefan.lang.shapebyte.features.workout.data.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.data.fixture.WorkoutHistoryDataSourceFixture
import kotlin.time.Instant

// TODO: consider using mock data from file
public object WorkoutHistoryPreviewDataProvider {
    private val datasource = WorkoutHistoryDataSourceFixture

    public val previewData: List<WorkoutHistoryEntry> by lazy {
        datasource.createHistoryEntries(
            Instant.parse("2024-12-24T00:00:00Z"),
            Instant.parse("2024-12-12T00:00:00Z"),
        ).map {
            WorkoutDataModule.workoutHistoryEntry(it)
        }
    }
}
