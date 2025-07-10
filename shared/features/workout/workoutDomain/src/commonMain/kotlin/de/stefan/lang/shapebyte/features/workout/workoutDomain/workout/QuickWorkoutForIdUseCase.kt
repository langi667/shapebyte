package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coreutils.api.logging.Logging
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

class QuickWorkoutForIdUseCase(
    private val repository: QuickWorkoutsRepository,
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
    coroutineScopeProvider: CoroutineScopeProviding,
) : BaseFeatureDataUseCase<Workout>(
    featureToggle = FeatureId.QUICK_WORKOUTS.name,
    logger = logger,
    scope = coroutineScopeProvider.createCoroutineScope(SupervisorJob()),
    dispatcher = coroutineContextProvider.iODispatcher(),
) {
    operator fun invoke(id: Int): SharedFlow<LoadState<Workout>> {
        return super.invoke(
            onDisabled = { QuickWorkoutsError.FeatureDisabled },
            onEnabled = { repository.workoutForId(id) },
        )
    }
}
