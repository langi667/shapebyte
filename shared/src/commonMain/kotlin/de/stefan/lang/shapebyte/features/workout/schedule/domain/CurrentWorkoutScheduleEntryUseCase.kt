package de.stefan.lang.shapebyte.features.workout.schedule.domain

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseDataUseCase
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CurrentWorkoutScheduleEntryUseCase(
    private val repository: WorkoutScheduleRepository,
    logger: Logging,
) : BaseDataUseCase<WorkoutScheduleEntry?>(logger) {

    operator fun invoke(scope: CoroutineScope): SharedFlow<LoadState<WorkoutScheduleEntry?>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)

            repository.currentWorkoutScheduleEntry().collect {
                mutableFlow.emit(LoadState.Success(it))
            }
        }

        return flow
    }
}
