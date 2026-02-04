package de.stefan.lang.core.di

import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

public interface FeatureGraph : KoinComponent {
    public val productionDiModule: Module
    public val testDiModule: Module
}
