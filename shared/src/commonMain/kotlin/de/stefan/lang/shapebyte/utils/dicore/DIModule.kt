package de.stefan.lang.shapebyte.utils.dicore

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

interface DIModule : KoinComponent {
    val module: Module
    val testModule: Module
}
