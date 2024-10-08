package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.shapebyte.utils.CountdownTimer
import de.stefan.lang.shapebyte.utils.Logger
import de.stefan.lang.shapebyte.utils.Logging
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import de.stefan.lang.shapebyte.utils.mocks.MockLogger
import org.koin.core.component.inject
import org.koin.dsl.module

object UtilsModule : DIModule {
    override val module = module {
        single<Logging> { Logger() }
        factory { CountdownTimer(logger = get()) }
    }

    override val testModule = module {
        single<Logging> { MockLogger() }
        factory { CountdownTimer(logger = get()) }
    }

    val logger: Logging by inject()
}
