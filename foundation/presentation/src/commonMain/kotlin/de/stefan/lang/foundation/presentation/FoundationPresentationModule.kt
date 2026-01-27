package de.stefan.lang.foundation.presentation

import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.foundation.presentation.contract.dimension.DimensionProvider
import de.stefan.lang.foundationCore.FoundationCoreModule
import org.koin.core.component.get

interface FoundationPresentationModuleProviding {
    fun dimensionProvider(): DimensionProvider
}

object FoundationPresentationModule :
    RootDIModule(
        allEnvironments = {
            single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
        },
        appEnvironmentOnly = {
        },
        testEnvironmentOnly = {
        },
        dependencies = listOf(FoundationCoreModule),
    ),
    FoundationPresentationModuleProviding {

    override fun dimensionProvider(): DimensionProvider = get()
}
