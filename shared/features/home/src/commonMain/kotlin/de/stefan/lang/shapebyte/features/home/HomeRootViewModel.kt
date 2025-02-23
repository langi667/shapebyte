package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.foundationCore.loadstate.asResultFlow
import de.stefan.lang.foundationUI.viewmodel.BaseViewModel
import de.stefan.lang.foundationUI.viewmodel.UIState
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.workoutData.Workout
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutDomain.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.QuickWorkoutsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeRootViewModel(
    private val currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase,
    private val recentHistoryUseCase: FetchRecentWorkoutHistoryUseCase,
    private val quickWorkoutsUseCase: QuickWorkoutsUseCase,
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {
    private val _state: MutableStateFlow<UIState> = MutableStateFlow(
        UIState.Idle,
    )

    override val state: StateFlow<UIState> = _state

    private val dataFlow: Flow<UIState.Data<HomeRootViewData>> = combine(
        mapCurrentWorkoutScheduleEntry(currentWorkoutScheduleEntryUseCase.flow),
        mapRecentHistory(recentHistoryUseCase.flow),
        mapQuickWorkouts(quickWorkoutsUseCase.flow),
    ) { currWorkoutScheduleEntry, recentHistory, quickWorkoutsState ->
        val data = HomeRootViewData(
            currWorkoutScheduleEntry = currWorkoutScheduleEntry,
            recentHistory = recentHistory,
            quickWorkouts = quickWorkoutsState,
        )

        logD("Received data $data")
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

        recentHistoryUseCase.invoke()
        currentWorkoutScheduleEntryUseCase.invoke()
        quickWorkoutsUseCase.invoke()
    }

    private fun mapRecentHistory(flow: Flow<LoadState<List<WorkoutScheduleEntry>>>) =
        flow
            .asResultFlow()
            .map { result ->
                when (result) {
                    is LoadState.Success -> result.data
                    else -> emptyList()
                }
            }
            .map { entries ->
                entries.map { entry -> WorkoutModule.workoutHistoryEntry(entry) }
            }

    private fun mapCurrentWorkoutScheduleEntry(flow: Flow<LoadState<WorkoutScheduleEntry?>>) =
        flow
            .asResultFlow()
            .map { result ->
                when (result) {
                    is LoadState.Success -> result.data
                    else -> null
                }
            }

    private fun mapQuickWorkouts(flow: Flow<LoadState<List<Workout>>>) =
        flow
            .asResultFlow()
            .map { result ->
                when (result) {
                    is LoadState.Success -> result.data
                    else -> emptyList()
                }
            }
}
