package de.stefan.lang.shapebyte.features.workout.workoutDomain

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundation.presentation.FoundationPresentationModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.features.workout.api.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.api.item.Item
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.api.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.api.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.QuickWorkoutsDatasourceMocks
import de.stefan.lang.shapebyte.features.workout.workoutData.mocks.WorkoutScheduleRepository
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsDatasource
import de.stefan.lang.shapebyte.features.workout.workoutData.workout.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.CurrentWorkoutScheduleEntryUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.FetchRecentWorkoutHistoryUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutForIdUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.workoutDomain.workout.QuickWorkoutsUseCaseImpl
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
    fun quickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase
    fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution
    fun itemsExecutionBuilder(): ItemsExecutionBuilder
}

object WorkoutDomainModule :
    RootModule(
        allEnvironments = {
            single<FetchRecentWorkoutHistoryUseCase> {
                FetchRecentWorkoutHistoryUseCaseImpl(
                    repository = get(),
                    logger = get(),
                    coroutineContextProviding = get(),
                    coroutineScopeProviding = get(),
                    loadFeatureToggleUseCase = get(),
                )
            }
            single<WorkoutScheduleRepository> { WorkoutScheduleRepository(datasource = get()) }
            single<CurrentWorkoutScheduleEntryUseCase> {

                CurrentWorkoutScheduleEntryUseCaseImpl(
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
                QuickWorkoutsUseCaseImpl(
                    repository = get(),
                    logger = get(),
                    scopeProvider = get(),
                    dispatcherProvider = get(),
                    loadFeatureToggleUseCase = get(),
                )
            }

            factory<QuickWorkoutForIdUseCase> {
                QuickWorkoutForIdUseCaseImpl(
                    repository = get(),
                    logger = get(),
                    coroutineContextProvider = get(),
                    coroutineScopeProvider = get(),
                    loadFeatureToggleUseCase = get(),
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
        dependencies = listOf(
            FoundationCoreModule,
            FoundationPresentationModule,
        ),
    ),
    WorkoutDomainModuleProviding {
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
    override fun quickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase = get()

    override fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution = get(
        parameters = {
            parametersOf(items)
        },
    )

    override fun itemsExecutionBuilder(): ItemsExecutionBuilder = get()
}
