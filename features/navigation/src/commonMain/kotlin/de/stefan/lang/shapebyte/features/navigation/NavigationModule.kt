package de.stefan.lang.shapebyte.features.navigation

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundation.presentation.FoundationPresentationModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationModuleContract
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestBuilding
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestResolving
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestResolver
import org.koin.core.component.get
object NavigationModule :
    RootModule(
        bindings = ModuleBindings(
            allEnvironments = {
                single { NavigationRequestBuilder() }
                single { NavigationRequestResolver() }
            },
        ),
        dependencies = listOf(
            FoundationCoreModule,
            FoundationPresentationModule,
        ),
    ),
    NavigationModuleContract {
    override fun navigationRequestBuilder(): NavigationRequestBuilding = get()
    override fun navigationRequestResolver(): NavigationRequestResolving = get()
}
