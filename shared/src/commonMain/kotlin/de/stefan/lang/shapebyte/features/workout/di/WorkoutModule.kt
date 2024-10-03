package de.stefan.lang.shapebyte.features.workout.di

import de.stefan.lang.shapebyte.features.workout.domain.TimedItemSetHandler
import org.koin.dsl.module

val workoutModule = module {
    single<TimedItemSetHandler> { TimedItemSetHandler(logger = get(), timer = get()) }
}
