package de.stefan.lang.shapebyte.features.workout.workoutData.mocks

import de.stefan.lang.coreutils.progress.Progress
import de.stefan.lang.foundationCore.loadstate.LoadState
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
