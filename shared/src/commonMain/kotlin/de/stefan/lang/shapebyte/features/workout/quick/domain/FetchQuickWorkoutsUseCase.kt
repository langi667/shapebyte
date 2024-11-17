package de.stefan.lang.shapebyte.features.workout.quick.domain

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseDataUseCase
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

// TODO: Test
class FetchQuickWorkoutsUseCase(
    private val repository: QuickWorkoutsRepository,
    private val quickWorkoutsFeatureToggleUseCase: QuickWorkoutsFeatureToggleUseCase,
    logger: Logging,
) : BaseDataUseCase<List<Workout>>(logger) {

    operator fun invoke(scope: CoroutineScope): SharedFlow<LoadState<List<Workout>>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)

            quickWorkoutsFeatureToggleUseCase()
                .collect { result ->
                    if (result.dataOrNull<FeatureToggle>()?.isEnabled == true) {
                        fetchQuickWorkouts(scope)
                    } else {
                        mutableFlow.emit(LoadState.Error(QuickWorkoutsError.FeatureDisabled))
                    }
                }
        }

        return flow
    }

    private fun fetchQuickWorkouts(scope: CoroutineScope) {
        scope.launch {
            repository.fetchQuickWorkouts().collect {
                mutableFlow.emit(LoadState.Success(it))
            }
        }
    }
}
