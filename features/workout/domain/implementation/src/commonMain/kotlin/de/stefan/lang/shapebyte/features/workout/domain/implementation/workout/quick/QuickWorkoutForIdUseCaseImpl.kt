package de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.SharedFlow

class QuickWorkoutForIdUseCaseImpl(
    private val repository: QuickWorkoutsRepository,
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
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
