package de.stefan.lang.shapebyte.features.workout.countdown

import de.stefan.lang.foundationUi.api.intent.UIIntent
import de.stefan.lang.shapebyte.features.workout.workoutData.item.ItemSet

sealed class CountdownItemSetsUIIntent : UIIntent {
    data class Start(
        val itemSets: List<ItemSet.Timed.Seconds>,
    ) : CountdownItemSetsUIIntent()
}
