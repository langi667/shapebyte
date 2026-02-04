package de.stefan.lang.shapebyte.features.workout.domain.contract

import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.data.contract.quick.QuickWorkoutsRepository
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecutionBuilding
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.history.FetchRecentWorkoutHistoryUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutForIdUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.quick.QuickWorkoutsUseCase
import de.stefan.lang.shapebyte.features.workout.domain.contract.workout.schedule.CurrentWorkoutScheduleEntryUseCase

public interface WorkoutDomainContract {
    public fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase
    public fun fetchRecentWorkoutHistoryUseCase(loadFeatureToggleUseCase: LoadFeatureToggleUseCase): FetchRecentWorkoutHistoryUseCase
    public fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase
    public fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution
    public fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution

    public fun quickWorkoutsUseCase(loadFeatureToggleUseCase: LoadFeatureToggleUseCase): QuickWorkoutsUseCase
    public fun quickWorkoutsUseCase(): QuickWorkoutsUseCase
    public fun quickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase
    public fun quickWorkoutForIdUseCase(
        repository: QuickWorkoutsRepository,
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): QuickWorkoutForIdUseCase

    public fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution
    public fun itemsExecutionBuilder(): ItemsExecutionBuilding
}
