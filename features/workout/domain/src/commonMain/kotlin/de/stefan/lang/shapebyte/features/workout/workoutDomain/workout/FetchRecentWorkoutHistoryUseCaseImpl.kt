package de.stefan.lang.shapebyte.features.workout.workoutDomain.workout

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.features.workout.api.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutHistoryRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

class FetchRecentWorkoutHistoryUseCaseImpl(
    private val repository: WorkoutHistoryRepository,
    logger: Logging,
    coroutineContextProviding: CoroutineContextProviding,
    coroutineScopeProviding: CoroutineScopeProviding,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : FetchRecentWorkoutHistoryUseCase(
    logger = logger,
    coroutineContextProviding = coroutineContextProviding,
    coroutineScopeProviding = coroutineScopeProviding,
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    override operator fun invoke(
        today: Instant,
    ): SharedFlow<LoadState<List<WorkoutScheduleEntry>>> = super.invoke(
        onDisabled = { HistoryError.FeatureDisabled },
        onEnabled = { fetchRecentHistory(today) },
    )

    private suspend fun fetchRecentHistory(today: Instant) = repository.historyForDates(
        date = today,
        pastDate = today.minus(14.days),
    )
}
