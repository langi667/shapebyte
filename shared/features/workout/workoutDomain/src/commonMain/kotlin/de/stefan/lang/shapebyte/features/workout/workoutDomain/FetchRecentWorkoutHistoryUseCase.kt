package de.stefan.lang.shapebyte.features.workout.workoutDomain

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProviding.CoroutineScopeProviding
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.FeatureId
import de.stefan.lang.shapebyte.featureToggles.domain.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutScheduleEntry
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
) : BaseFeatureDataUseCase<List<WorkoutScheduleEntry>>(
    featureToggle = FeatureId.RECENT_HISTORY.name,
    logger = logger,
    dispatcher = coroutineContextProviding.iODispatcher(),
    scope = coroutineScopeProviding.createCoroutineScope(SupervisorJob()),
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
