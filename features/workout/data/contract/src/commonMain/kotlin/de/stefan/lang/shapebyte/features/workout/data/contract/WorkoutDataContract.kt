package de.stefan.lang.shapebyte.features.workout.data.contract

import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleRepository

interface WorkoutDataContract {
    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry
    fun workoutHistoryRepository(): WorkoutHistoryRepository
    fun workoutScheduleRepository(): WorkoutScheduleRepository
    fun quickWorkoutsRepository(): QuickWorkoutsRepository
}
