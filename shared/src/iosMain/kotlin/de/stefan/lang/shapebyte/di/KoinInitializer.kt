package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.utils.Logger
import de.stefan.lang.shapebyte.utils.Logging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun startKoin(){
    val modules = commonModule

    startKoin {
        modules(modules)
    }
}

class LoggerInjector: KoinComponent {
    val logger: Logging by inject()
}