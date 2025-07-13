package de.stefan.lang.shapebyte.features.workout.api.quick

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.BaseFeatureDataUseCase
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.Workout
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow

abstract class QuickWorkoutForIdUseCase(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
    coroutineScopeProvider: CoroutineScopeProviding,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
): BaseFeatureDataUseCase<Workout>(
    featureToggle = FeatureId.QUICK_WORKOUTS.name,
    logger = logger,
    scope = coroutineScopeProvider.createCoroutineScope(SupervisorJob()),
    dispatcher = coroutineContextProvider.iODispatcher(),
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    abstract operator fun invoke(id: Int): SharedFlow<LoadState<Workout>>
}