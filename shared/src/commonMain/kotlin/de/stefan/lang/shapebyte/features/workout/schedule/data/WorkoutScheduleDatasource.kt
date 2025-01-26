package de.stefan.lang.shapebyte.features.workout.schedule.data

import de.stefan.lang.shapebyte.shared.loading.data.LoadState

interface WorkoutScheduleDatasource {
    suspend fun currentWorkoutScheduleEntry(): LoadState.Result<WorkoutScheduleEntry?>
}
