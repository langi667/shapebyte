package de.stefan.lang.shapebyte.features.navigation

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.foundation.presentation.FoundationPresentationModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestResolver
import org.koin.core.component.get

interface NavigationModuleProviding {
    fun navigationRequestBuilder(): NavigationRequestBuilder
    fun navigationRequestResolver(): NavigationRequestResolver
}

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
    NavigationModuleProviding {
    override fun navigationRequestBuilder(): NavigationRequestBuilder = get()
    override fun navigationRequestResolver(): NavigationRequestResolver = get()
}
