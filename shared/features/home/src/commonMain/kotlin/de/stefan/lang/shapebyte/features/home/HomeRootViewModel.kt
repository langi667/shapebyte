package de.stefan.lang.shapebyte.features.home

import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.foundationCore.loadstate.asResultFlow
import de.stefan.lang.foundationUI.viewmodel.BaseViewModel
import de.stefan.lang.foundationUI.viewmodel.UIState
import de.stefan.lang.navigation.NavigationRequestBuilder
import de.stefan.lang.navigation.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.Workout
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeRootViewModel(
    private val navigationHandler: NavigationRequestHandling,
    private val currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase,
    private val recentHistoryUseCase: FetchRecentWorkoutHistoryUseCase,
    private val quickWorkoutsUseCase: QuickWorkoutsUseCase,
    private val navigationRequestBuilder: NavigationRequestBuilder,
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

    fun onQuickWorkoutSelected(workout: Workout) {
        navigationHandler.handleNavigationRequest(
            navigationRequestBuilder.quickWorkout(workout.id),
        )
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
