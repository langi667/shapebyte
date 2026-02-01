package de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.schedule

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CurrentWorkoutScheduleEntryUseCaseImpl(
    private val repository: WorkoutScheduleRepository,
    coroutineContextProviding: CoroutineContextProviding,
    coroutineScopeProviding: CoroutineScopeProviding,
    logger: Logging,
) : CurrentWorkoutScheduleEntryUseCase(logger) {
    private val scope: CoroutineScope = coroutineScopeProviding.createCoroutineScope(
        context = coroutineContextProviding.iODispatcher(),
    )

    override operator fun invoke(): Flow<LoadState<WorkoutScheduleEntry?>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)
            mutableFlow.emit(repository.currentWorkoutScheduleEntry())
        }

        return flow
    }
}
