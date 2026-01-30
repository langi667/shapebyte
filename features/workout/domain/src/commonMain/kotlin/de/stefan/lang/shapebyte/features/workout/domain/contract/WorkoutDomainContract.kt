package de.stefan.lang.shapebyte.features.workout.domain.contract

import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.schedule.CurrentWorkoutScheduleEntryUseCase

/*
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

 */

interface WorkoutDomainContract {
    fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase
    fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution
    fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution

    fun quickWorkoutsUseCase(): QuickWorkoutsUseCase
    fun quickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase
    fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution
    fun itemsExecutionBuilder(): ItemsExecutionBuilder
}
