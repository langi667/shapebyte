package de.stefan.lang.shapebyte.features.workout.quick.domain

import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding
import de.stefan.lang.shapebyte.utils.logging.Logging
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
