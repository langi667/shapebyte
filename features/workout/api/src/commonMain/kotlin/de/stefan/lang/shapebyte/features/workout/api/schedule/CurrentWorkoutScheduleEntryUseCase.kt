package de.stefan.lang.shapebyte.features.workout.api.schedule

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.usecase.BaseDataUseCase
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.flow.Flow

abstract class CurrentWorkoutScheduleEntryUseCase(logger: Logging) :
    BaseDataUseCase<WorkoutScheduleEntry?>(logger) {
    abstract operator fun invoke(): Flow<LoadState<WorkoutScheduleEntry?>>
}
