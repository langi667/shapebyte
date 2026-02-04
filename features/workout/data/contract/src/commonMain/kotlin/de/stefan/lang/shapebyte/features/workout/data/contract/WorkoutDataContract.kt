package de.stefan.lang.shapebyte.features.workout.data.contract

import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleRepository

public interface WorkoutDataContract {
    public fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry
    public fun workoutHistoryRepository(): WorkoutHistoryRepository
    public fun workoutScheduleRepository(): WorkoutScheduleRepository
    public fun quickWorkoutsRepository(): QuickWorkoutsRepository
}
