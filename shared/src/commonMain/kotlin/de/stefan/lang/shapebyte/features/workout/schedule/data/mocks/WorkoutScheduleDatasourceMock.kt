package de.stefan.lang.shapebyte.features.workout.schedule.data.mocks

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.utils.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

@Suppress("MagicNumber")
object WorkoutScheduleDatasourceMock : WorkoutScheduleDatasource {
    override fun currentWorkoutScheduleEntry(): Flow<WorkoutScheduleEntry> = flow {
        val entry = WorkoutScheduleEntry(
            id = "1",
            name = "Leg Day",
            date = Clock.System.now(),
            progress = Progress(0.7f),
        )
        emit(entry)
    }
}
