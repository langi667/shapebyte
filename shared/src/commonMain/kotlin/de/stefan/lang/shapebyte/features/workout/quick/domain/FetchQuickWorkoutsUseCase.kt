package de.stefan.lang.shapebyte.features.workout.quick.domain

import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FetchQuickWorkoutsUseCase(
    private val repository: QuickWorkoutsRepository,
    logger: Logging,
) : BaseFeatureDataUseCase<List<Workout>>(FeatureId.QUICK_WORKOUTS.name, logger) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(scope: CoroutineScope): SharedFlow<LoadState<List<Workout>>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)
            collectFromFeatureToggle(scope) { enabled ->
                if (enabled) {
                    fetchQuickWorkouts()
                } else {
                    emitError(QuickWorkoutsError.FeatureDisabled)
                }
            }
        }

        return flow
    }

    private suspend fun fetchQuickWorkouts() {
        repository.fetchQuickWorkouts()
            .collectLatest { workouts ->
                mutableFlow.emit(LoadState.Success(workouts))
            }
    }
}
