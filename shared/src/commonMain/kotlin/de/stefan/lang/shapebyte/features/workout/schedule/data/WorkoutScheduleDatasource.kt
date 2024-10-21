package de.stefan.lang.shapebyte.features.workout.schedule.data

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import kotlinx.coroutines.flow.Flow

interface WorkoutScheduleDatasource {
    fun currentWorkoutScheduleEntry(): Flow<WorkoutScheduleEntry?>
}
