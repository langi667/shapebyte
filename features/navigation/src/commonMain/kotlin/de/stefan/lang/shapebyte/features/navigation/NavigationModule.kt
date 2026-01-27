package de.stefan.lang.shapebyte.features.navigation

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.core.di.RootDIModule
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
    RootDIModule(
        providedInstances = DIModuleDeclaration(
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
