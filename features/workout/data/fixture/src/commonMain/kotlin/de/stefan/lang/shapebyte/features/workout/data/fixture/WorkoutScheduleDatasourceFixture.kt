package de.stefan.lang.shapebyte.features.workout.data.fixture

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Suppress("MagicNumber")
public object WorkoutScheduleDatasourceFixture : WorkoutScheduleDatasource {
    @OptIn(ExperimentalTime::class)
    public override suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?> {
        val entry = WorkoutScheduleEntry(
            id = "1",
            name = "Leg Day",
            date = Clock.System.now(),
            progress = Progress(0.7f),
        )

        return LoadState.Success(entry)
    }
}
