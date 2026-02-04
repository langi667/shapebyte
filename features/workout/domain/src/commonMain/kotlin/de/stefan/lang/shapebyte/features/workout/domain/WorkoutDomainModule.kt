package de.stefan.lang.shapebyte.features.workout.domain

import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.domain.contract.WorkoutDomainContract
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecutionBuilding
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.schedule.CurrentWorkoutScheduleEntryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.generated.Dependencies
import de.stefan.lang.shapebyte.features.workout.domain.generated.Module
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecutionBuilder
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecutionImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.repetative.RepetitiveItemExecutionImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.timed.TimedItemExecutionImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.history.FetchRecentWorkoutHistoryUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick.QuickWorkoutForIdUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.quick.QuickWorkoutsUseCaseImpl
import de.stefan.lang.shapebyte.features.workout.domain.implementation.workout.schedule.CurrentWorkoutScheduleEntryUseCaseImpl
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

public object WorkoutDomainModule :
    Module(
        globalBindings = {
            single<FetchRecentWorkoutHistoryUseCase> { (loadFeatureToggleUseCase: LoadFeatureToggleUseCase) ->
                FetchRecentWorkoutHistoryUseCaseImpl(
                    repository = Dependencies.workoutHistoryRepository(),
                    logger = Dependencies.logger(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                    coroutineScopeProvider = Dependencies.coroutineScopeProvider(),
                    loadFeatureToggleUseCase = loadFeatureToggleUseCase,
                )
            }
            single<CurrentWorkoutScheduleEntryUseCase> {
                CurrentWorkoutScheduleEntryUseCaseImpl(
                    repository = Dependencies.workoutScheduleRepository(),
                    logger = Dependencies.logger(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                    coroutineScopeProvider = Dependencies.coroutineScopeProvider(),
                )
            }

            factory<TimedItemExecution> { (item: Item, sets: List<ItemSet.Timed.Seconds>) ->
                TimedItemExecutionImpl(
                    item = item,
                    sets = sets,
                    logger = Dependencies.logger(),
                )
            }

            factory<RepetitiveItemExecution> { (item: Item, sets: List<ItemSet.Repetition>) ->
                RepetitiveItemExecutionImpl(
                    item = item,
                    sets = sets,
                    logger = Dependencies.logger(),
                )
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

            factory<ItemsExecution> { (items: List<ItemExecuting<*, *>>) ->
                ItemsExecutionImpl(items, Dependencies.logger())
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
    ),
    WorkoutDomainContract {
    public override fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase =
        fetchRecentWorkoutHistoryUseCase(get())

    public override fun fetchRecentWorkoutHistoryUseCase(
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): FetchRecentWorkoutHistoryUseCase = get { parametersOf(loadFeatureToggleUseCase) }

    public override fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase = get()
    public override fun createTimedItemExecution(
        item: Item,
        sets: List<ItemSet.Timed.Seconds>,
    ): TimedItemExecution =
        get(
            parameters = {
                parametersOf(item, sets)
            },
        )

    public override fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution = get(
        parameters = {
            parametersOf(item, sets)
        },
    )

    public override fun quickWorkoutsUseCase(): QuickWorkoutsUseCase =
        quickWorkoutsUseCase(get())
    public override fun quickWorkoutsUseCase(
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): QuickWorkoutsUseCase = get(parameters = { parametersOf(loadFeatureToggleUseCase) })

    public override fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution = get(
        parameters = {
            parametersOf(items)
        },
    )

    public override fun quickWorkoutForIdUseCase(
        repository: QuickWorkoutsRepository,
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): QuickWorkoutForIdUseCase = get(
        parameters = {
            parametersOf(repository, loadFeatureToggleUseCase)
        },
    )

    public override fun quickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase = quickWorkoutForIdUseCase(
        Dependencies.quickWorkoutsRepository(),
        get(),
    )

    public override fun itemsExecutionBuilder(): ItemsExecutionBuilding = get()
}
