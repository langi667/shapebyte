package de.stefan.lang.shapebyte.features.workout.api.countdown

import de.stefan.lang.foundation.presentation.contract.intent.UIIntent
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet

sealed class CountdownItemSetsUIIntent : UIIntent {
    data class Start(
        val itemSets: List<ItemSet.Timed.Seconds>,
    ) : CountdownItemSetsUIIntent()
}
