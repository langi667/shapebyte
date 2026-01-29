package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.api.Workout
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.flow.SharedFlow

class QuickWorkoutsUseCaseImpl(
    private val repository: QuickWorkoutsRepository,
    scopeProvider: CoroutineScopeProviding,
    dispatcherProvider: CoroutineContextProviding,
    logger: Logging,
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
