package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.BaseFeatureDataUseCase
import de.stefan.lang.featureToggles.api.FeatureToggleLoading
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.featureToggles.api.FeatureId
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleEntry
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

class FetchRecentWorkoutHistoryUseCase(
    private val repository: WorkoutHistoryRepository,
    logger: Logging,
    coroutineContextProviding: CoroutineContextProviding,
    coroutineScopeProviding: CoroutineScopeProviding,
    featureToggleLoading: FeatureToggleLoading,
) : BaseFeatureDataUseCase<List<WorkoutScheduleEntry>>(
    featureToggle = FeatureId.RECENT_HISTORY.name,
    logger = logger,
    dispatcher = coroutineContextProviding.iODispatcher(),
    scope = coroutineScopeProviding.createCoroutineScope(SupervisorJob()),
    featureToggleLoading = featureToggleLoading,
) {
    operator fun invoke(
        today: Instant = Clock.System.now(),
    ): SharedFlow<LoadState<List<WorkoutScheduleEntry>>> = super.invoke(
        onDisabled = { HistoryError.FeatureDisabled },
        onEnabled = { fetchRecentHistory(today) },
    )

    private suspend fun fetchRecentHistory(today: Instant) = repository.historyForDates(
        date = today,
        pastDate = today.minus(14.days),
    )
}
