package de.stefan.lang.shapebyte.di

import org.koin.core.context.startKoin

fun startKoin(){
    val modules = CommonMainModules

    startKoin {
        modules(modules)
    }
}