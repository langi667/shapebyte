package de.stefan.lang.shapebyte.features.workout.item.data

/**
Represents any kind of workout item, such as exercise, breaks, countdown, cooldown, warm up ...
 */

interface Item {
    val name: String
}

object None : Item {
    override val name: String = "None"
}
