package de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.history

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.history.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.data.contract.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.HistoryError
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

public class FetchRecentWorkoutHistoryUseCaseImpl(
    private val repository: WorkoutHistoryRepository,
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : FetchRecentWorkoutHistoryUseCase(
    logger = logger,
    coroutineContextProvider = coroutineContextProvider,
    coroutineScopeProvider = coroutineScopeProvider,
    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
) {
    public override operator fun invoke(
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
