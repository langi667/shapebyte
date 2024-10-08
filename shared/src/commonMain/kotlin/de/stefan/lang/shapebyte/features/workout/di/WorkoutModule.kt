package de.stefan.lang.shapebyte.features.workout.di

import de.stefan.lang.shapebyte.features.workout.domain.ItemSetsHandler
import de.stefan.lang.shapebyte.features.workout.domain.sethandler.DefaultItemSetHandler
import de.stefan.lang.shapebyte.features.workout.domain.sethandler.TimedItemSetHandler
import de.stefan.lang.shapebyte.features.workout.ui.CountdownItemSetsViewModel
import org.koin.dsl.module

val workoutModule = module {
    factory<ItemSetsHandler> {
        ItemSetsHandler(
            logger = get(),
            timedSetHandler = get(),
            defaultSetHandler = get(),
        )
    }

    factory<TimedItemSetHandler> { TimedItemSetHandler(logger = get(), timer = get()) }
    factory<DefaultItemSetHandler> { DefaultItemSetHandler() }
    factory { CountdownItemSetsViewModel(logger = get(), itemSetsHandler = get()) }
}

val workoutTestModule = module {
    factory<ItemSetsHandler> {
        ItemSetsHandler(
            logger = get(),
            timedSetHandler = get(),
            defaultSetHandler = get(),
        )
    }

    factory<TimedItemSetHandler> { TimedItemSetHandler(logger = get(), timer = get()) }
    factory<DefaultItemSetHandler> { DefaultItemSetHandler() }
}
