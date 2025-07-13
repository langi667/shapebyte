package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.api.Workout
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
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
    loadFeatureToggleUseCase
) {
    override operator fun invoke(): SharedFlow<LoadState<List<Workout>>> = super.invoke(
        onDisabled = { QuickWorkoutsError.FeatureDisabled },
    ) {
        fetchQuickWorkouts()
    }

    private suspend fun fetchQuickWorkouts() = repository.fetchQuickWorkouts()
}
