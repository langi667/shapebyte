package de.stefan.lang.foundationUI

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.foundationUi.api.dimension.DimensionProvider
import org.koin.core.component.get

interface FoundationUIModuleProviding {
    fun dimensionProvider(): DimensionProvider
}

object FoundationUIModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
        },
        appEnvironmentOnly = {
        },
        testEnvironmentOnly = {
        },
    ),
    FoundationUIModuleProviding {

    override fun dimensionProvider(): DimensionProvider = get()
}
