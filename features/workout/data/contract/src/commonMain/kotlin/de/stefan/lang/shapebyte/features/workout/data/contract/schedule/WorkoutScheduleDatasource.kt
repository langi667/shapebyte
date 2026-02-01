package de.stefan.lang.shapebyte.features.workout.data.contract.schedule

import de.stefan.lang.foundation.core.contract.loadstate.LoadState

interface WorkoutScheduleDatasource {
    suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?>
}
