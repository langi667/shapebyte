package de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureId
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.Workout
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow

abstract class QuickWorkoutForIdUseCase(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
    coroutineScopeProvider: CoroutineScopeProviding,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : BaseFeatureDataUseCase<Workout>(
    featureToggle = FeatureId.QUICK_WORKOUTS.name,
    logger = logger,
    scope = coroutineScopeProvider.createCoroutineScope(SupervisorJob()),
    dispatcher = coroutineContextProvider.iODispatcher(),
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    abstract operator fun invoke(id: Int): SharedFlow<LoadState<Workout>>
}
