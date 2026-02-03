package de.stefan.lang.shapebyte.features.workout.domain.implementation

import de.stefan.lang.shapebyte.features.workout.data.contract.item.Item
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.implementation.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.repetative.RepetitiveItemExecution
import de.stefan.lang.shapebyte.features.workout.domain.implementation.timed.TimedItemExecution
import de.stefan.lang.utils.logging.LoggingModule

object ImplementationModule {
    fun createTimedItemExecution(item: Item, sets: List<ItemSet.Timed.Seconds>): TimedItemExecution {
        return TimedItemExecution(
            item = item,
            sets = sets,
            logger = LoggingModule.logger(),
        )
    }

    fun createRepetitiveItemExecution(
        item: Item,
        sets: List<ItemSet.Repetition>,
    ): RepetitiveItemExecution {
        return RepetitiveItemExecution(
            item = item,
            sets = sets,
            logger = LoggingModule.logger(),
        )
    }

    fun createItemsExecution(items: List<ItemExecuting<*, *>>): ItemsExecution {
        return ItemsExecution(items, LoggingModule.logger())
    }
}
