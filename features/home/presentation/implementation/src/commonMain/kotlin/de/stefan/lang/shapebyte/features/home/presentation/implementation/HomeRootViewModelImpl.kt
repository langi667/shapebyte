package de.stefan.lang.shapebyte.features.home.presentation.implementation

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.loadstate.asResultFlow
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootUIIntent
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewData
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomeRootViewModel
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestBuilding
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.api.Workout
import de.stefan.lang.shapebyte.features.workout.api.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.api.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.api.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

// TODO: make internal again
public class HomeRootViewModelImpl(
    private val navigationHandler: NavigationRequestHandling,
    private val currentWorkoutScheduleEntryUseCase: CurrentWorkoutScheduleEntryUseCase,
    private val recentHistoryUseCase: FetchRecentWorkoutHistoryUseCase,
    private val quickWorkoutsUseCase: QuickWorkoutsUseCase,
    private val navigationRequestBuilder: NavigationRequestBuilding,
    private val dateTimeStringFormatter: DateTimeStringFormatter,
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : HomeRootViewModel(logger, coroutineContextProvider) {

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

    override fun intent(intent: HomeRootUIIntent) {
        when (intent) {
            is HomeRootUIIntent.Update -> update()
            is HomeRootUIIntent.QuickWorkoutSelected -> onQuickWorkoutSelected(intent.workout)
        }
    }

    private fun update() {
        _state.value = UIState.Loading // TODO: maybe refresh state, if data is available

        recentHistoryUseCase.invoke(today = Clock.System.now())
        currentWorkoutScheduleEntryUseCase.invoke()
        quickWorkoutsUseCase.invoke()
    }

    private fun onQuickWorkoutSelected(workout: Workout) {
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
                entries.map { entry -> WorkoutHistoryEntry(entry, dateTimeStringFormatter) }
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
