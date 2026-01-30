package de.stefan.lang.core.di

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

interface FeatureGraph : KoinComponent {
    val productionModules: Module
    val testModules: Module
}
