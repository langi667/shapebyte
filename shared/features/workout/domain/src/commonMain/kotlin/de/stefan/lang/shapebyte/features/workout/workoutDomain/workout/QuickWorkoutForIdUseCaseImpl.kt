package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.api.Workout
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import kotlinx.coroutines.flow.SharedFlow

class QuickWorkoutForIdUseCaseImpl(
    private val repository: QuickWorkoutsRepository,
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
    coroutineScopeProvider: CoroutineScopeProviding,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : QuickWorkoutForIdUseCase(
    logger = logger,
    coroutineContextProvider = coroutineContextProvider,
    coroutineScopeProvider = coroutineScopeProvider,
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    override operator fun invoke(id: Int): SharedFlow<LoadState<Workout>> {
        return super.invoke(
            onDisabled = { QuickWorkoutsError.FeatureDisabled },
            onEnabled = { repository.workoutForId(id) },
        )
    }
}
