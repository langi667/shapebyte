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

class QuickWorkoutForIdUseCase(
    private val repository: QuickWorkoutsRepository,
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
    coroutineScopeProvider: CoroutineScopeProviding,
) : BaseFeatureDataUseCase<Workout> (
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
