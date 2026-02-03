package de.stefan.lang.foundation.presentation

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundation.presentation.contract.FoundationPresentationContract
import de.stefan.lang.foundation.presentation.contract.dimension.DimensionProvider
import de.stefan.lang.shapebyte.foundation.presentation.generated.Dependencies
import org.koin.core.component.get

object FoundationPresentationModule :
    RootModule(
        bindings = ModuleBindings(
            allEnvironments = {
                single<DimensionProvider> {
                    DimensionProvider(
                        deviceSizeCategoryProvider = Dependencies.deviceSizeCategoryProvider(),
                    )
                }
            },
        ),
        dependencies = Dependencies.modules,
    ),
    FoundationPresentationContract {

    override fun dimensionProvider(): DimensionProvider = get()
}
