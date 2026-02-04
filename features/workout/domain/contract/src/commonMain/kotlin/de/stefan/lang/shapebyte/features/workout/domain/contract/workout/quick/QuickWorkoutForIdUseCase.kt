package de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureId
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow

public abstract class QuickWorkoutForIdUseCase(
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : BaseFeatureDataUseCase<Workout>(
    featureToggle = FeatureId.QUICK_WORKOUTS.name,
    logger = logger,
    scope = coroutineScopeProvider.createCoroutineScope(SupervisorJob()),
    dispatcher = coroutineContextProvider.iODispatcher(),
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    public abstract operator fun invoke(id: Int): SharedFlow<LoadState<Workout>>
}
