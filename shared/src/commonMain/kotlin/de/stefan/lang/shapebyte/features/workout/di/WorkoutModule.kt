package de.stefan.lang.shapebyte.features.workout.di

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.history.data.mocks.WorkoutHistoryDataSourceMocks
import de.stefan.lang.shapebyte.features.workout.history.domain.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.item.repetitive.domain.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.item.shared.data.Item
import de.stefan.lang.shapebyte.features.workout.item.shared.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.shared.domain.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.item.shared.domain.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.item.timed.ui.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.quick.data.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.quick.data.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.quick.domain.FetchQuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.schedule.data.mocks.WorkoutScheduleDatasourceMock
import de.stefan.lang.shapebyte.features.workout.schedule.domain.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.utils.dicore.DIModuleDeclaration
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface WorkoutModuleProviding {
    fun countdownItemSetsViewModel(): CountdownItemSetsViewModel
    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry

    fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase

    fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution
    fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution

    fun fetchQuickWorkoutsUseCase(): FetchQuickWorkoutsUseCase
    fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution
}

object WorkoutModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<WorkoutHistoryRepository> { WorkoutHistoryRepository(dataSource = get()) }
            single<FetchRecentWorkoutHistoryUseCase> {
                FetchRecentWorkoutHistoryUseCase(
                    repository = get(),
                    logger = get(),
                )
            }
            single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }
            single<CurrentWorkoutScheduleEntryUseCase> {
                CurrentWorkoutScheduleEntryUseCase(
                    repository = get(),
                    logger = get(),
                )
            }

            factory { CountdownItemSetsViewModel(logger = get()) }
            factory<WorkoutHistoryEntry> { (entry: WorkoutScheduleEntry) ->
                WorkoutHistoryEntry(
                    entry = entry, dateStringFormatter = get(),
                )
            }
            factory<TimedItemExecution> { (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
                TimedItemExecution(item, sets, get())
            }

            factory<RepetitiveItemExecution> { (item: Item, sets: List<ItemSet.Repetition>) ->
                RepetitiveItemExecution(item, sets, get())
            }

            // TODO: change once implemented !!!
            single<QuickWorkoutsDatasource> { QuickWorkoutsDatasourceMocks() }
            single<QuickWorkoutsRepository> {
                QuickWorkoutsRepository(
                    quickWorkoutsDataSource = get(),
                    logger = get(),
                )
            }

            single<FetchQuickWorkoutsUseCase> {
                FetchQuickWorkoutsUseCase(
                    repository = get(),
                    logger = get(),
                )
            }

            factory<ItemsExecution> { (items: List<ItemExecuting<*, *>>) ->
                ItemsExecution(items, get())
            }
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
    ): TimedItemExecution =
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

    override fun fetchQuickWorkoutsUseCase(): FetchQuickWorkoutsUseCase = get()
    override fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution = get(
        parameters = {
            parametersOf(items)
        },
    )
}
