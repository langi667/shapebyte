package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.utils.mocks.MockLogger
import org.koin.dsl.module

val utilsModule = module {
    single<Logging> { Logger() }
    factory { CountdownTimer(logger = get()) }
}

val testUtilsModule = module {
    single<Logging> { MockLogger() }
    factory { CountdownTimer(logger = get()) }
}
