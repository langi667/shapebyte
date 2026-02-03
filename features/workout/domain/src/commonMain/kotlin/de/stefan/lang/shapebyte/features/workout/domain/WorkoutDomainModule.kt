package de.stefan.lang.shapebyte.features.workout.domain

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.domain.contract.WorkoutDomainContract
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecutionBuilding
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.TimedItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.generated.Dependencies
import de.stefan.lang.shapebyte.features.workout.domain.implementation.ImplementationModule
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.domain.implementation.repetative.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.history.FetchRecentWorkoutHistoryUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick.QuickWorkoutForIdUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick.QuickWorkoutsUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.schedule.CurrentWorkoutScheduleEntryUseCaseImpl
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

object WorkoutDomainModule :
    RootModule(
        globalBindings = {
            single<FetchRecentWorkoutHistoryUseCase> { (loadFeatureToggleUseCase: LoadFeatureToggleUseCase) ->
                FetchRecentWorkoutHistoryUseCaseImpl(
                    repository = Dependencies.workoutHistoryRepository(),
                    logger = Dependencies.logger(),
                    coroutineContextProviding = Dependencies.coroutineContextProvider(),
                    coroutineScopeProviding = Dependencies.coroutineScopeProvider(),
                    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
                )
            }
            single<CurrentWorkoutScheduleEntryUseCase> {
                CurrentWorkoutScheduleEntryUseCaseImpl(
                    repository = Dependencies.workoutScheduleRepository(),
                    logger = Dependencies.logger(),
                    coroutineContextProviding = Dependencies.coroutineContextProvider(),
                    coroutineScopeProviding = Dependencies.coroutineScopeProvider(),
                )
            }

            factory<TimedItemExecuting> { (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
                ImplementationModule.createTimedItemExecution(item, sets)
            }

            factory<RepetitiveItemExecuting> { (item: Item, sets: List<ItemSet.Repetition>) ->
                ImplementationModule.createRepetitiveItemExecution(item, sets)
            }

            single<QuickWorkoutsUseCase> { (featureTogglesUseCase: LoadFeatureToggleUseCase) ->
                QuickWorkoutsUseCaseImpl(
                    repository = Dependencies.quickWorkoutsRepository(),
                    logger = Dependencies.logger(),
                    scopeProvider = Dependencies.coroutineScopeProvider(),
                    dispatcherProvider = Dependencies.coroutineContextProvider(),
                    loadFeatureToggleUseCase = featureTogglesUseCase,
                )
            }

            factory<QuickWorkoutForIdUseCase> { (repository: QuickWorkoutsRepository, loadFeatureToggleUseCase: LoadFeatureToggleUseCase) ->
                QuickWorkoutForIdUseCaseImpl(
                    repository = repository,
                    logger = Dependencies.logger(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                    coroutineScopeProvider = Dependencies.coroutineScopeProvider(),
                    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
                )
            }

            factory<ItemsExecuting> { (items: List<ItemExecuting<*, *>>) ->
                ItemsExecution(items, Dependencies.logger())
            }

            single<ItemsExecutionBuilding> {
                ItemsExecutionBuilder(
                    logger = Dependencies.logger(),
                )
            }
        },
        productionBindings = {
        },
        testBindings = {
        },
        dependencies = Dependencies.modules,
    ),
    WorkoutDomainContract {
    override fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase =
        fetchRecentWorkoutHistoryUseCase(get())

    override fun fetchRecentWorkoutHistoryUseCase(
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): FetchRecentWorkoutHistoryUseCase = get { parametersOf(loadFeatureToggleUseCase) }

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

    override fun quickWorkoutsUseCase(): QuickWorkoutsUseCase =
        quickWorkoutsUseCase(get())
    override fun quickWorkoutsUseCase(
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): QuickWorkoutsUseCase = get(parameters = { parametersOf(loadFeatureToggleUseCase) })

    override fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecuting = get(
        parameters = {
            parametersOf(items)
        },
    )

    override fun quickWorkoutForIdUseCase(
        repository: QuickWorkoutsRepository,
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): QuickWorkoutForIdUseCase = get(
        parameters = {
            parametersOf(repository, loadFeatureToggleUseCase)
        },
    )

    override fun quickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase = quickWorkoutForIdUseCase(
        Dependencies.quickWorkoutsRepository(),
        get(),
    )

    override fun itemsExecutionBuilder(): ItemsExecutionBuilding = get()
}
