package de.stefan.lang.shapebyte.features.workout.workoutData.item

import de.stefan.lang.shapebyte.features.workout.api.item.Item
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet

data class ItemWithSets(val item: Item, val sets: List<ItemSet>)
