package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import de.stefan.lang.shapebyte.features.workout.WorkoutModule.createTimedItemExecution
import de.stefan.lang.shapebyte.features.workout.api.countdown.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.api.history.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.api.item.Item
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.api.schedule.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.api.timed.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.countdown.CountdownItemSetsViewModelImpl
import de.stefan.lang.shapebyte.features.workout.presentation.timed.TimedWorkoutViewModelImpl
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.workoutDomain.WorkoutDomainModule
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecution
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface WorkoutModuleProviding {
    fun countdownItemSetsViewModel(): CountdownItemSetsViewModel
    fun timedWorkoutViewModel(navHandler: NavigationRequestHandling): TimedWorkoutViewModel
    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution
}

object WorkoutModule :
    RootModule(
        bindings = ModuleBindings(
            allEnvironments = {
                factory<TimedWorkoutViewModel> { (navHandler: NavigationRequestHandling) ->
                    TimedWorkoutViewModelImpl(
                        navigationHandler = navHandler,
                        quickWorkoutForIdUseCase = get(),
                        itemsExecutionBuilder = get(),
                        dateStringFormatter = get(),
                        logger = get(),
                        audioPlayer = get(),
                        coroutineContextProvider = get(),
                    )
                }

                factory {
                    CountdownItemSetsViewModelImpl(
                        logger = get(),
                        coroutineContextProvider = get(),
                        timedHandlerFactory = { item, sets -> createTimedItemExecution(item, sets) },
                    )
                }
                factory<WorkoutHistoryEntry> { (entry: WorkoutScheduleEntry) ->
                    WorkoutHistoryEntry(
                        entry = entry, dateStringFormatter = get(),
                    )
                }
                factory<TimedItemExecution> { (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
                    TimedItemExecution(
                        item = item,
                        sets = sets,
                        logger = get(),
                    )
                }
            },
            appEnvironmentOnly = {
            },
            testEnvironmentOnly = {
            },
        ),

        dependencies = listOf(
            WorkoutDataModule,
            WorkoutDomainModule,
        ),
    ),
    WorkoutModuleProviding {
    override fun countdownItemSetsViewModel(): CountdownItemSetsViewModel = get()
    override fun timedWorkoutViewModel(navHandler: NavigationRequestHandling): TimedWorkoutViewModel = get(
        parameters = {
            parametersOf(navHandler)
        },
    )

    override fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry =
        get(
            parameters = {
                parametersOf(scheduleEntry)
            },
        )

    override fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution {
        return WorkoutDomainModule.createTimedItemExecution(item, sets)
    }
}
