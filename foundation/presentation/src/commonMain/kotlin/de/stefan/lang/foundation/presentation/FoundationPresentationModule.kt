package de.stefan.lang.foundation.presentation

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.foundation.presentation.contract.dimension.DimensionProvider
import org.koin.core.component.get

interface FoundationPresentationModuleProviding {
    fun dimensionProvider(): DimensionProvider
}

object FoundationPresentationModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
        },
        appEnvironmentOnly = {
        },
        testEnvironmentOnly = {
        },
    ),
    FoundationPresentationModuleProviding {

    override fun dimensionProvider(): DimensionProvider = get()
}
