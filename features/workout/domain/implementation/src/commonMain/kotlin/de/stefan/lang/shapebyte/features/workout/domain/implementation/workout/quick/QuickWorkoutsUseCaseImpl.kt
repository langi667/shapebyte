package de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutsUseCase
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.SharedFlow

class QuickWorkoutsUseCaseImpl(
    private val repository: QuickWorkoutsRepository,
    scopeProvider: CoroutineScopeProvider,
    dispatcherProvider: CoroutineContextProvider,
    logger: Logger,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : QuickWorkoutsUseCase(
    scopeProvider,
    dispatcherProvider,
    logger,
    loadFeatureToggleUseCase,
) {
    override operator fun invoke(): SharedFlow<LoadState<List<Workout>>> = super.invoke(
        onDisabled = { QuickWorkoutsError.FeatureDisabled },
    ) {
        fetchQuickWorkouts()
    }

    private suspend fun fetchQuickWorkouts() = repository.fetchQuickWorkouts()
}
