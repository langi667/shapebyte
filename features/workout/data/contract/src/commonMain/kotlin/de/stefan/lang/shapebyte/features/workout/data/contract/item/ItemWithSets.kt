package de.stefan.lang.shapebyte.features.workout.data.contract.item

public data class ItemWithSets public constructor(
    public val item: Item,
    public val sets: List<ItemSet>,
)
