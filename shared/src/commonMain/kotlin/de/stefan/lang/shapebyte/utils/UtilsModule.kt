package de.stefan.lang.shapebyte.utils

import org.koin.dsl.module

val utilsModule = module {
    single<Logger> { Logger() }
}