package de.stefan.lang.foundation.presentation

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundation.presentation.contract.FoundationPresentationContract
import de.stefan.lang.foundation.presentation.contract.dimension.DimensionProvider
import de.stefan.lang.foundationCore.FoundationCoreModule
import org.koin.core.component.get

object FoundationPresentationModule :
    RootModule(
        bindings = ModuleBindings(
            allEnvironments = {
                single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
            },
        ),
        dependencies = listOf(FoundationCoreModule),
    ),
    FoundationPresentationContract {

    override fun dimensionProvider(): DimensionProvider = get()
}
