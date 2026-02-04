package de.stefan.lang.shapebyte.features.workout.data.contract.schedule

import de.stefan.lang.foundation.core.contract.loadstate.LoadState

public interface WorkoutScheduleDatasource {
    public suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?>
}
