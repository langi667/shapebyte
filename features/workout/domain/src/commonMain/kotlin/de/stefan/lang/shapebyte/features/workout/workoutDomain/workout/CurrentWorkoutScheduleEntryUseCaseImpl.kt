package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleRepository
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
