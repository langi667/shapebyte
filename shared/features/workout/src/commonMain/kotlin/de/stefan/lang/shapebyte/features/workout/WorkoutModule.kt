package de.stefan.lang.shapebyte.features.workout

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.history.data.mocks.WorkoutHistoryDataSourceMocks
import de.stefan.lang.shapebyte.features.workout.history.domain.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.item.core.data.Item
import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.item.repetitive.domain.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.item.timed.ui.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.item.timed.ui.TimedWorkoutViewModel
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.quick.data.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.quick.domain.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.quick.domain.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.schedule.data.mocks.WorkoutScheduleDatasourceMock
import de.stefan.lang.shapebyte.features.workout.schedule.domain.CurrentWorkoutScheduleEntryUseCase
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface WorkoutModuleProviding {
    fun countdownItemSetsViewModel(): CountdownItemSetsViewModel
    fun timedWorkoutViewModel(): TimedWorkoutViewModel

    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry

    fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase

    fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecution
    fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution

    fun quickWorkoutsUseCase(): QuickWorkoutsUseCase
    fun createQuickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase
    fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution
    fun itemsExecutionBuilder(): ItemsExecutionBuilder
}

object WorkoutModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<TimedWorkoutViewModel> {
                TimedWorkoutViewModel(
                    quickWorkoutForIdUseCase = get(),
                    itemsExecutionBuilder = get(),
                    dateStringFormatter = get(),
                    logger = get(),
                    audioPlayer = get(),
                    coroutineContextProvider = get(),
                )
            }

            single<WorkoutHistoryRepository> { WorkoutHistoryRepository(dataSource = get()) }
            single<FetchRecentWorkoutHistoryUseCase> {
                FetchRecentWorkoutHistoryUseCase(
                    repository = get(),
                    logger = get(),
                    coroutineContextProviding = get(),
                    coroutineScopeProviding = get(),
                )
            }
            single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }
            single<CurrentWorkoutScheduleEntryUseCase> {
                CurrentWorkoutScheduleEntryUseCase(
                    repository = get(),
                    logger = get(),
                    coroutineContextProviding = get(),
                    coroutineScopeProviding = get(),
                )
            }

            factory {
                CountdownItemSetsViewModel(
                    logger = get(),
                    coroutineContextProvider = get(),
                )
            }
            factory<WorkoutHistoryEntry> { (entry: WorkoutScheduleEntry) ->
                WorkoutHistoryEntry(
                    entry = entry, dateStringFormatter = get(),
                )
            }
            factory<de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecution> { (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
                de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecution(
                    item,
                    sets,
                    get()
                )
            }

            factory<RepetitiveItemExecution> { (item: Item, sets: List<ItemSet.Repetition>) ->
                RepetitiveItemExecution(item, sets, get())
            }

            // TODO: change once implemented !!!
            single<QuickWorkoutsDatasource> { QuickWorkoutsDatasourceMocks() }
            single<QuickWorkoutsRepository> {
                QuickWorkoutsRepository(
                    dataSource = get(),
                    logger = get(),
                )
            }

            single<QuickWorkoutsUseCase> {
                QuickWorkoutsUseCase(
                    repository = get(),
                    logger = get(),
                    scopeProvider = get(),
                    dispatcherProvider = get(),
                )
            }

            factory<QuickWorkoutForIdUseCase> {
                QuickWorkoutForIdUseCase(
                    repository = get(),
                    logger = get(),
                    coroutineContextProvider = get(),
                    coroutineScopeProvider = get(),
                )
            }

            factory<ItemsExecution> { (items: List<ItemExecuting<*, *>>) ->
                ItemsExecution(items, get())
            }

            single<ItemsExecutionBuilder> { ItemsExecutionBuilder() }
        },
        appEnvironmentOnly = {
            single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceMocks } // TODO: change once implemented !!!
            single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceMock } // TODO: change once implemented !!!
        },
        testEnvironmentOnly = {
            single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceMocks }
            single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceMock }
        },

    ),
    WorkoutModuleProviding {
    override fun countdownItemSetsViewModel(): CountdownItemSetsViewModel = get()
    override fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry =
        get(
            parameters = {
                parametersOf(scheduleEntry)
            },
        )

    override fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase = get()
    override fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase = get()
    override fun createTimedItemExecution(
        item: Item,
        sets: List<ItemSet.Timed.Seconds>,
    ): de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecution =
        get(
            parameters = {
                parametersOf(item, sets)
            },
        )

    override fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution = get(
        parameters = {
            parametersOf(item, sets)
        },
    )

    override fun quickWorkoutsUseCase(): QuickWorkoutsUseCase = get()
    override fun createQuickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase = get()

    override fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution = get(
        parameters = {
            parametersOf(items)
        },
    )

    override fun itemsExecutionBuilder(): ItemsExecutionBuilder = get()
    override fun timedWorkoutViewModel(): TimedWorkoutViewModel = get()
}
