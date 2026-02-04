package de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.schedule

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

public class CurrentWorkoutScheduleEntryUseCaseImpl(
    private val repository: WorkoutScheduleRepository,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
    logger: Logger,
) : CurrentWorkoutScheduleEntryUseCase(logger) {
    private val scope: CoroutineScope = coroutineScopeProvider.createCoroutineScope(
        context = coroutineContextProvider.iODispatcher(),
    )

    public override operator fun invoke(): Flow<LoadState<WorkoutScheduleEntry?>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)
            mutableFlow.emit(repository.currentWorkoutScheduleEntry())
        }

        return flow
    }
}
