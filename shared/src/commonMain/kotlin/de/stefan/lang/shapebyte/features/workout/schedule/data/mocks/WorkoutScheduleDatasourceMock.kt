package de.stefan.lang.shapebyte.features.workout.schedule.data.mocks

import de.stefan.lang.core.progress.Progress
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import kotlinx.datetime.Clock

@Suppress("MagicNumber")
object WorkoutScheduleDatasourceMock : WorkoutScheduleDatasource {
    override suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?> {
        val entry = WorkoutScheduleEntry(
            id = "1",
            name = "Leg Day",
            date = Clock.System.now(),
            progress = Progress(0.7f),
        )

        return LoadState.Success(entry)
    }
}
