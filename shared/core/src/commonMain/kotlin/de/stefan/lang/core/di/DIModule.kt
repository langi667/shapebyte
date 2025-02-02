package de.stefan.lang.core.di

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

interface DIModule : KoinComponent {
    val module: Module
    val testModule: Module
}

