package de.stefan.lang.shapebyte.features.workout.api.history

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.BaseFeatureDataUseCase
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

abstract class FetchRecentWorkoutHistoryUseCase(
    logger: Logging,
    coroutineContextProviding: CoroutineContextProviding,
    coroutineScopeProviding: CoroutineScopeProviding,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : BaseFeatureDataUseCase<List<WorkoutScheduleEntry>> (
    featureToggle = FeatureId.RECENT_HISTORY.name,
    logger = logger,
    dispatcher = coroutineContextProviding.iODispatcher(),
    scope = coroutineScopeProviding.createCoroutineScope(SupervisorJob()),
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    abstract operator fun invoke(today: Instant): SharedFlow<LoadState<List<WorkoutScheduleEntry>>>
    operator fun invoke(): SharedFlow<LoadState<List<WorkoutScheduleEntry>>> = invoke(
        today = Clock.System.now(),
    )
}
