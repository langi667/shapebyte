package de.stefan.lang.shapebyte.features.workout.quick.domain

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProviding.CoroutineScopeProviding
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.FeatureId
import de.stefan.lang.shapebyte.featureToggles.domain.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow

class QuickWorkoutsUseCase(
    private val repository: QuickWorkoutsRepository,
    scopeProvider: CoroutineScopeProviding,
    dispatcherProvider: CoroutineContextProviding,
    logger: Logging,
) : BaseFeatureDataUseCase<List<Workout>>(
    featureToggle = FeatureId.QUICK_WORKOUTS.name,
    logger = logger,
    scope = scopeProvider.createCoroutineScope(SupervisorJob()),
    dispatcher = dispatcherProvider.iODispatcher(),
) {
    operator fun invoke(): SharedFlow<LoadState<List<Workout>>> = super.invoke(
        onDisabled = { QuickWorkoutsError.FeatureDisabled },
    ) {
        fetchQuickWorkouts()
    }

    private suspend fun fetchQuickWorkouts() = repository.fetchQuickWorkouts()
}
