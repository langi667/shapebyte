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

interface WorkoutDomainContract {
    fun fetchRecentWorkoutHistoryUseCase(): FetchRecentWorkoutHistoryUseCase
    fun fetchRecentWorkoutHistoryUseCase(loadFeatureToggleUseCase: LoadFeatureToggleUseCase): FetchRecentWorkoutHistoryUseCase
    fun currentWorkoutScheduleEntryUseCase(): CurrentWorkoutScheduleEntryUseCase
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution
    fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution

    fun quickWorkoutsUseCase(loadFeatureToggleUseCase: LoadFeatureToggleUseCase): QuickWorkoutsUseCase
    fun quickWorkoutsUseCase(): QuickWorkoutsUseCase
    fun quickWorkoutForIdUseCase(): QuickWorkoutForIdUseCase
    fun quickWorkoutForIdUseCase(
        repository: QuickWorkoutsRepository,
        loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    ): QuickWorkoutForIdUseCase

    fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution
    fun itemsExecutionBuilder(): ItemsExecutionBuilding
}
