package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.FeatureId
import de.stefan.lang.shapebyte.featureTogglesDomain.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsError
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.Workout
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
