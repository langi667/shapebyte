package de.stefan.lang.shapebyte.features.navigation

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationModuleContract
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestResolver
import de.stefan.lang.shapebyte.features.navigation.generated.Module
import de.stefan.lang.shapebyte.features.navigation.implementation.NavigationRequestBuilderImpl
import de.stefan.lang.shapebyte.features.navigation.implementation.NavigationRequestResolverImpl
import org.koin.core.component.get
public object NavigationModule :
    Module(
        globalBindings = {
            single<NavigationRequestBuilder> { NavigationRequestBuilderImpl() }
            single<NavigationRequestResolver> { NavigationRequestResolverImpl() }
        },
    ),
    NavigationModuleContract {
    public override fun navigationRequestBuilder(): NavigationRequestBuilder = get()
    public override fun navigationRequestResolver(): NavigationRequestResolver = get()
}
