package de.stefan.lang.shapebyte.features.workout.di

import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryDataSource
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutHistoryRepository
import de.stefan.lang.shapebyte.features.workout.history.data.WorkoutScheduleEntry
import de.stefan.lang.shapebyte.features.workout.history.data.mocks.WorkoutHistoryDataSourceMocks
import de.stefan.lang.shapebyte.features.workout.history.domain.RecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.history.ui.WorkoutHistoryEntry
import de.stefan.lang.shapebyte.features.workout.item.data.Item
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.domain.DefaultItemSetHandler
import de.stefan.lang.shapebyte.features.workout.item.domain.ItemSetsHandler
import de.stefan.lang.shapebyte.features.workout.item.domain.RepetitionItemSetHandler
import de.stefan.lang.shapebyte.features.workout.item.domain.repetitive.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.item.domain.timed.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.item.domain.timed.TimedItemSetHandler
import de.stefan.lang.shapebyte.features.workout.item.ui.timed.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleDatasource
import de.stefan.lang.shapebyte.features.workout.schedule.data.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.schedule.data.mocks.WorkoutScheduleDatasourceMock
import de.stefan.lang.shapebyte.features.workout.schedule.domain.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

interface WorkoutModuleProviding {
    fun countdownItemSetsViewModel(): CountdownItemSetsViewModel
    fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry
    fun recentWorkoutHistoryUseCase(): RecentWorkoutHistoryUseCase
    fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution
    fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution
}

object WorkoutModule : DIModule, WorkoutModuleProviding {
    override val module = module {
        single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceMocks } // TODO: change once implemented !!!
        single<WorkoutHistoryRepository> { WorkoutHistoryRepository(dataSource = get()) }
        single<RecentWorkoutHistoryUseCase> {
            RecentWorkoutHistoryUseCase(
                repository = get(),
                logger = get(),
            )
        }
        single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceMock } // TODO: change once implemented !!!
        single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }
        single<CurrentWorkoutScheduleEntryUseCase> {
            CurrentWorkoutScheduleEntryUseCase(
                repository = get(),
                logger = get(),
            )
        }

        factory<ItemSetsHandler> {
            ItemSetsHandler(
                logger = get(),
                timedSetHandler = get(),
                repetitionSetHandler = get(),
                defaultSetHandler = get(),
            )
        }

        factory<TimedItemSetHandler> { TimedItemSetHandler(logger = get(), timer = get()) }
        factory<RepetitionItemSetHandler> { RepetitionItemSetHandler(logger = get()) }
        factory<DefaultItemSetHandler> { DefaultItemSetHandler() }
        factory { CountdownItemSetsViewModel(logger = get(), itemSetsHandler = get()) }

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
    }

    override val testModule = module {
        single<WorkoutHistoryDataSource> { WorkoutHistoryDataSourceMocks }
        single<WorkoutHistoryRepository> { WorkoutHistoryRepository(dataSource = get()) }

        single<RecentWorkoutHistoryUseCase> {
            RecentWorkoutHistoryUseCase(
                repository = get(),
                logger = get(),
            )
        }

        single<WorkoutScheduleDatasource> { WorkoutScheduleDatasourceMock } // TODO: change once implemented !!!
        single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }

        single<CurrentWorkoutScheduleEntryUseCase> {
            CurrentWorkoutScheduleEntryUseCase(
                repository = get(),
                logger = get(),
            )
        }

        factory<ItemSetsHandler> {
            ItemSetsHandler(
                logger = get(),
                timedSetHandler = get(),
                repetitionSetHandler = get(),
                defaultSetHandler = get(),
            )
        }

        factory<TimedItemSetHandler> { TimedItemSetHandler(logger = get(), timer = get()) }
        factory<RepetitionItemSetHandler> { RepetitionItemSetHandler(logger = get()) }
        factory<DefaultItemSetHandler> { DefaultItemSetHandler() }
        factory<WorkoutHistoryEntry> { (entry: WorkoutScheduleEntry) ->
            WorkoutHistoryEntry(
                entry = entry, dateStringFormatter = get(),
            )
        }
        factory<TimedItemExecution> {
                (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
            TimedItemExecution(item, sets, get())
        }
        factory<RepetitiveItemExecution> { (item: Item, sets: List<ItemSet.Repetition>) ->
            RepetitiveItemExecution(item, sets, get())
        }
    }

    override fun countdownItemSetsViewModel(): CountdownItemSetsViewModel = get()
    override fun workoutHistoryEntry(scheduleEntry: WorkoutScheduleEntry): WorkoutHistoryEntry =
        get(
            parameters = {
                parametersOf(scheduleEntry)
            },
        )

    override fun recentWorkoutHistoryUseCase(): RecentWorkoutHistoryUseCase = get()
    override fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase = get()
    override fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution =
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
}
