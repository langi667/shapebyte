package de.stefan.lang.shapebyte.features.workout.history.domain

import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseFeatureDataUseCase
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

class FetchRecentWorkoutHistoryUseCase(
    private val repository: WorkoutHistoryRepository,
    logger: Logging,
) : BaseFeatureDataUseCase<List<WorkoutScheduleEntry>>(FeatureId.RECENT_HISTORY.name, logger) {

    operator fun invoke(
        scope: CoroutineScope,
        today: Instant = Clock.System.now(),
    ): SharedFlow<LoadState<List<WorkoutScheduleEntry>>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)

            collectFromFeatureToggle(scope) { enabled ->
                if (enabled) {
                    fetchRecentHistory(today)
                } else {
                    emitError(HistoryError.FeatureDisabled)
                }
            }
        }

        return mutableFlow
    }

    private suspend fun fetchRecentHistory(today: Instant) {
        repository.historyForDates(today, today.minus(14.days)).collect {
            mutableFlow.emit(LoadState.Success(it))
        }
    }
}
