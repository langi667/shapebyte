package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.shapebyte.utils.CountdownTimer
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import de.stefan.lang.shapebyte.utils.logging.Logger
import de.stefan.lang.shapebyte.utils.logging.Logging
import de.stefan.lang.shapebyte.utils.mocks.SilentLogger
import org.koin.dsl.module

object UtilsModule : DIModule {
    override val module = module {
        single<Logging> { Logger() }
        factory { CountdownTimer(logger = get()) }
    }

    override val testModule = module {
        single<Logging> { SilentLogger() }
        factory { CountdownTimer(logger = get()) }
    }
}
