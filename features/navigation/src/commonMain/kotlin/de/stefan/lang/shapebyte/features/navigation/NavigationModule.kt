package de.stefan.lang.shapebyte.features.navigation

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationModuleContract
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestResolver
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestBuilderImpl
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestResolverImpl
import de.stefan.lang.shapebyte.features.navigation.generated.Dependencies
import de.stefan.lang.shapebyte.features.navigation.generated.Module
import org.koin.core.component.get
object NavigationModule :
    Module(
        globalBindings = {
            single<NavigationRequestBuilder> { NavigationRequestBuilderImpl() }
            single<NavigationRequestResolver> { NavigationRequestResolverImpl() }
        }
    ),
    NavigationModuleContract {
    override fun navigationRequestBuilder(): NavigationRequestBuilder = get()
    override fun navigationRequestResolver(): NavigationRequestResolver = get()
}
