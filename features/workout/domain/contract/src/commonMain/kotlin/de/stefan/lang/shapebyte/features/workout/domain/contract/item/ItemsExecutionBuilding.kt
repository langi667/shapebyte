package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType

interface ItemsExecutionBuilding {
    fun buildWith(workoutType: WorkoutType): ItemsExecuting
}
