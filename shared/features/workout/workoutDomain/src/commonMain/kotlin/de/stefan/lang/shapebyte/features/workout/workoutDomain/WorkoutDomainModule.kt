package de.stefan.lang.shapebyte.features.workout.workoutDomain

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.workoutData.WorkoutDataModuleProviding
import de.stefan.lang.shapebyte.features.workout.workoutData.item.Item
import de.stefan.lang.shapebyte.features.workout.workoutData.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutsUseCase
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface WorkoutDomainModuleProviding {
    fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase

    fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution
    fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution

    fun quickWorkoutsUseCase(): QuickWorkoutsUseCase
    fun createQuickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase
    fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution
    fun itemsExecutionBuilder(): ItemsExecutionBuilder
}

object WorkoutDomainModule :
    DIModuleDeclaration(
        allEnvironments = {
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

            factory<TimedItemExecution> { (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
                TimedItemExecution(
                    item = item,
                    sets = sets,
                    logger = get(),
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
        },
        testEnvironmentOnly = {
        },

    ),
    WorkoutDomainModuleProviding,
    WorkoutDataModuleProviding by WorkoutDataModule {
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

    override fun quickWorkoutsUseCase(): QuickWorkoutsUseCase = get()
    override fun createQuickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase = get()

    override fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution = get(
        parameters = {
            parametersOf(items)
        },
    )

    override fun itemsExecutionBuilder(): ItemsExecutionBuilder = get()
}
