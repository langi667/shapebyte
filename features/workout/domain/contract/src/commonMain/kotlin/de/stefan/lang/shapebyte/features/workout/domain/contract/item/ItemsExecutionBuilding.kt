package de.stefan.lang.shapebyte.features.workout.domain.contract.item

import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutType

public interface ItemsExecutionBuilding {
    public fun buildWith(workoutType: WorkoutType): ItemsExecution
}
