package de.stefan.lang.shapebyte.features.workout.history.domain

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseDataUseCase
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

class RecentWorkoutHistoryUseCase(
    private val repository: WorkoutHistoryRepository,
    logger: Logging,
) : BaseDataUseCase<List<WorkoutScheduleEntry>>(logger) {

    operator fun invoke(
        scope: CoroutineScope,
        today: Instant = Clock.System.now(),
    ) = scope.launch {
        mutableFlow.emit(LoadState.Loading)
        repository.historyForDates(today, today.minus(14.days)).collect {
            mutableFlow.emit(LoadState.Success(it))
        }
    }
}
