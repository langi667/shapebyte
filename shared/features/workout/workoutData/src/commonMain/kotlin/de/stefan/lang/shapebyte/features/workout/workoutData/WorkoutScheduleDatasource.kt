package de.stefan.lang.shapebyte.features.workout.workoutData

import de.stefan.lang.foundationCore.loadstate.LoadState

interface WorkoutScheduleDatasource {
    suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?>
}
