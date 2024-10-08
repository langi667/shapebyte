package de.stefan.lang.shapebyte.features.workout.di

import de.stefan.lang.shapebyte.features.workout.domain.ItemSetsHandler
import de.stefan.lang.shapebyte.features.workout.domain.sethandler.DefaultItemSetHandler
import de.stefan.lang.shapebyte.features.workout.domain.sethandler.TimedItemSetHandler
import de.stefan.lang.shapebyte.features.workout.ui.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import org.koin.core.component.inject
import org.koin.dsl.module

object WorkoutModule : DIModule {
    override val module = module {
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

    override val testModule = module {
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

    val countdownItemSetsViewModel: CountdownItemSetsViewModel by inject()
}
