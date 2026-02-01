package de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.history

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.HistoryError
import de.stefan.lang.utils.logging.contract.Logging
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
