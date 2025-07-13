package de.stefan.lang.shapebyte.features.workout.workoutData.mocks

import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry

interface WorkoutScheduleDatasource {
    suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?>
}
