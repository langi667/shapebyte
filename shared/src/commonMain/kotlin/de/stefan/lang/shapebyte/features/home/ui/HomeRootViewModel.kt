package de.stefan.lang.shapebyte.features.home.ui

import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.history.domain.RecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.schedule.domain.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.shared.viewmodel.ui.BaseViewModel
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class HomeRootViewModelViewData(
    val currWorkoutScheduleEntry: WorkoutScheduleEntry? = null,
    val recentHistory: List<WorkoutHistoryEntry> = emptyList(),
)

class HomeRootViewModel(
    private val currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase,
    private val recentHistoryUseCase: RecentWorkoutHistoryUseCase,
    logger: Logging,
) : BaseViewModel(logger) {
    private val _state: MutableStateFlow<UIState> = MutableStateFlow(
        UIState.Idle,
    )

    override val state: StateFlow<UIState> = _state

    private val dataFlow: Flow<UIState.Data<HomeRootViewModelViewData>> = combine(
        currentWorkoutScheduleEntryUseCase.dataFlow,
        recentHistoryUseCase.dataFlow.map {
            it.map { currEntry ->
                WorkoutModule.workoutHistoryEntry(currEntry)
            }
        },
    ) { currentWorkoutScheduleEntry, recentHistory ->
        val data = HomeRootViewModelViewData(
            currWorkoutScheduleEntry = currentWorkoutScheduleEntry,
            recentHistory = recentHistory,
        )

        UIState.Data(data)
    }

    init {
        scope.launch {
            dataFlow.collect {
                _state.value = it
            }
        }
    }

    fun update() {
        _state.value = UIState.Loading // TODO: maybe refresh state, if data is available

        recentHistoryUseCase.invoke(scope)
        currentWorkoutScheduleEntryUseCase.invoke(scope)
    }
}
