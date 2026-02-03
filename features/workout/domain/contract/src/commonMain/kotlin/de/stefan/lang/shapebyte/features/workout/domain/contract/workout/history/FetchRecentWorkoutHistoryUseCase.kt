package de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureId
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

abstract class FetchRecentWorkoutHistoryUseCase(
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : BaseFeatureDataUseCase<List<WorkoutScheduleEntry>>(
    featureToggle = FeatureId.RECENT_HISTORY.name,
    logger = logger,
    dispatcher = coroutineContextProvider.iODispatcher(),
    scope = coroutineScopeProvider.createCoroutineScope(SupervisorJob()),
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    abstract operator fun invoke(today: Instant): SharedFlow<LoadState<List<WorkoutScheduleEntry>>>
    operator fun invoke(): SharedFlow<LoadState<List<WorkoutScheduleEntry>>> = invoke(
        today = Clock.System.now(),
    )
}
