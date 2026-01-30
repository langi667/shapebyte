package de.stefan.lang.shapebyte.features.workout.data.contract

import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry

interface WorkoutDataContract {
    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry
}
