package de.stefan.lang.shapebyte.features.navigation

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationModuleContract
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestResolver
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestBuilderImpl
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestResolverImpl
import de.stefan.lang.shapebyte.features.navigation.generated.Dependencies
import org.koin.core.component.get
object NavigationModule :
    RootModule(
        bindings = ModuleBindings(
            allEnvironments = {
                single<NavigationRequestBuilder> { NavigationRequestBuilderImpl() }
                single<NavigationRequestResolver> { NavigationRequestResolverImpl() }
            },
        ),
        dependencies = Dependencies.modules,
    ),
    NavigationModuleContract {
    override fun navigationRequestBuilder(): NavigationRequestBuilder = get()
    override fun navigationRequestResolver(): NavigationRequestResolver = get()
}
