package de.stefan.lang.shapebyte.features.workout.api.schedule

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.foundationCore.api.usecase.BaseDataUseCase
import kotlinx.coroutines.flow.Flow

abstract class CurrentWorkoutScheduleEntryUseCase(logger: Logging) :
    BaseDataUseCase<WorkoutScheduleEntry?>(logger) {
    abstract operator fun invoke(): Flow<LoadState<WorkoutScheduleEntry?>>
}
