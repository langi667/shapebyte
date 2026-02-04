package de.stefan.lang.shapebyte.features.workout.data.contract.schedule

import de.stefan.lang.foundation.core.contract.loadstate.LoadState

// TODO: Test
public class WorkoutScheduleRepository public constructor(
    private val datasource: WorkoutScheduleDatasource,
) {
    public suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?> =
        datasource.currentWorkoutScheduleEntry()
}
