package de.stefan.lang.shapebyte.di

import org.koin.core.context.startKoin

fun startKoin(){
    startKoin {
        modules(CommonMainModule.modules)
    }
}