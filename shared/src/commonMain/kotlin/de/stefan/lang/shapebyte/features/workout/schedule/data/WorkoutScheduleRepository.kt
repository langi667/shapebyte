package de.stefan.lang.shapebyte.features.workout.schedule.data

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import kotlinx.coroutines.flow.Flow

// TODO: Test
class WorkoutScheduleRepository(
    private val datasource: WorkoutScheduleDatasource,
) {
    fun currentWorkoutScheduleEntry(): Flow<WorkoutScheduleEntry?> {
        return datasource.currentWorkoutScheduleEntry()
    }
}
