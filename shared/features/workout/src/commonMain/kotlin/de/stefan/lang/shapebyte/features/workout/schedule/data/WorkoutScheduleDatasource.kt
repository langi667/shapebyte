package de.stefan.lang.shapebyte.features.workout.schedule.data

import de.stefan.lang.foundationCore.loadstate.LoadState

interface WorkoutScheduleDatasource {
    suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?>
}
