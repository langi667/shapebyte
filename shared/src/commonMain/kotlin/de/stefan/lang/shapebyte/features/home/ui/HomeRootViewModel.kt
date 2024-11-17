package de.stefan.lang.shapebyte.features.home.ui

import de.stefan.lang.shapebyte.features.workout.core.data.Workout
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.history.domain.RecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.quick.domain.FetchQuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.quick.ui.QuickWorkoutsUIState
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
import org.koin.core.component.get

class HomeRootViewModel(
    private val currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase,
    private val recentHistoryUseCase: RecentWorkoutHistoryUseCase,
    private val fetchQuickWorkoutsUseCase: FetchQuickWorkoutsUseCase,
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
        fetchQuickWorkoutsUseCase.flow.map {
            it.dataOrNull<List<Workout>>()?.let { workouts ->
                if (workouts.isNotEmpty()) {
                    QuickWorkoutsUIState.Enabled(workouts)
                } else {
                    QuickWorkoutsUIState.Hidden
                }
            } ?: QuickWorkoutsUIState.Hidden
        },
    ) { currentWorkoutScheduleEntry, recentHistory, quickWorkoutsState ->
        val data = HomeRootViewModelViewData(
            currWorkoutScheduleEntry = currentWorkoutScheduleEntry,
            recentHistory = recentHistory,
            quickWorkoutsState = quickWorkoutsState,
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
        fetchQuickWorkoutsUseCase.invoke(scope)
    }
}
