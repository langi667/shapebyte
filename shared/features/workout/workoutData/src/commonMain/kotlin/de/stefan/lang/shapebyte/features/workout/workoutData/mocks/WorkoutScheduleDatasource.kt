package de.stefan.lang.shapebyte.features.workout.workoutData.mocks

import de.stefan.lang.foundationCore.api.loadstate.LoadState

interface WorkoutScheduleDatasource {
    suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?>
}
