package de.stefan.lang.shapebyte.features.workout.api.quick

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.BaseFeatureDataUseCase
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.Workout
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow

abstract class QuickWorkoutsUseCase(
    scopeProvider: CoroutineScopeProviding,
    dispatcherProvider: CoroutineContextProviding,
    logger: Logging,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : BaseFeatureDataUseCase<List<Workout>>(
    featureToggle = FeatureId.QUICK_WORKOUTS.name,
    logger = logger,
    scope = scopeProvider.createCoroutineScope(SupervisorJob()),
    dispatcher = dispatcherProvider.iODispatcher(),
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    abstract operator fun invoke(): SharedFlow<LoadState<List<Workout>>>
}
