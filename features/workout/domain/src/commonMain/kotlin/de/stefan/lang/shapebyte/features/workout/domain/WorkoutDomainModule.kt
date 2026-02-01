package de.stefan.lang.shapebyte.features.workout.domain

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.features.workout.data.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.WorkoutDomainContract
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecutionBuilding
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.TimedItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.generated.GeneratedDependencies
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.domain.implementation.repetative.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.timed.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.history.FetchRecentWorkoutHistoryUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick.QuickWorkoutForIdUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick.QuickWorkoutsUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.schedule.CurrentWorkoutScheduleEntryUseCaseImpl
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

object WorkoutDomainModule :
    RootModule(
        globalBindings = {
            single<FetchRecentWorkoutHistoryUseCase> {
                FetchRecentWorkoutHistoryUseCaseImpl(
                    repository = WorkoutDataModule.workoutHistoryRepository(),
                    logger = get(),
                    coroutineContextProviding = get(),
                    coroutineScopeProviding = get(),
                    loadFeatureToggleUseCase = get(),
                )
            }
            single<CurrentWorkoutScheduleEntryUseCase> {
                CurrentWorkoutScheduleEntryUseCaseImpl(
                    repository = WorkoutDataModule.workoutScheduleRepository(),
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

            single<QuickWorkoutsUseCase> {
                QuickWorkoutsUseCaseImpl(
                    repository = WorkoutDataModule.quickWorkoutsRepository(),
                    logger = get(),
                    scopeProvider = get(),
                    dispatcherProvider = get(),
                    loadFeatureToggleUseCase = get(),
                )
            }

            factory<QuickWorkoutForIdUseCase> {
                QuickWorkoutForIdUseCaseImpl(
                    repository = WorkoutDataModule.quickWorkoutsRepository(),
                    logger = get(),
                    coroutineContextProvider = get(),
                    coroutineScopeProvider = get(),
                    loadFeatureToggleUseCase = get(),
                )
            }

            factory<ItemsExecuting> { (items: List<ItemExecuting<*, *>>) ->
                ItemsExecution(items, get())
            }

            single<ItemsExecutionBuilding> {
                ItemsExecutionBuilder(
                    logger = get(),
                )
            }
        },
        productionBindings = {
        },
        testBindings = {
        },
        dependencies = GeneratedDependencies.modules,
    ),
    WorkoutDomainContract {
    override fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase = get()
    override fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase = get()
    override fun createTimedItemExecution(
        item: Item,
        sets: List<ItemSet.Timed.Seconds>,
    ): TimedItemExecuting =
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

    override fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecuting = get(
        parameters = {
            parametersOf(items)
        },
    )

    override fun itemsExecutionBuilder(): ItemsExecutionBuilding = get()
}
