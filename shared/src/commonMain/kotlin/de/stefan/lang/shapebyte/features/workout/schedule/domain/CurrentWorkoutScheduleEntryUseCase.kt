package de.stefan.lang.shapebyte.features.workout.schedule.domain

import de.stefan.lang.coreutils.coroutines.CoroutineContextProviding
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CurrentWorkoutScheduleEntryUseCase(
    private val repository: WorkoutScheduleRepository,
    coroutineContextProviding: CoroutineContextProviding,
    coroutineScopeProviding: CoroutineScopeProviding,
    logger: Logging,
) : BaseDataUseCase<WorkoutScheduleEntry?>(logger) {
    private val scope: CoroutineScope = coroutineScopeProviding.createCoroutineScope(
        context = coroutineContextProviding.iODispatcher(),
    )

    operator fun invoke(): Flow<LoadState<WorkoutScheduleEntry?>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)
            mutableFlow.emit(repository.currentWorkoutScheduleEntry())
        }

        return flow
    }
}
