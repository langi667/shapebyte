package de.stefan.lang.shapebyte.features.navigation

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.foundation.FoundationModule
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestResolver
import org.koin.core.component.get

interface NavigationModuleProviding {
    fun navigationRequestBuilder(): NavigationRequestBuilder
    fun navigationRequestResolver(): NavigationRequestResolver
}

object NavigationModule :
    RootDIModule(
        providedModule = DIModuleDeclaration(
            allEnvironments = {
                single { NavigationRequestBuilder() }
                single { NavigationRequestResolver() }
            },
        ),
        diModules = listOf(
            FoundationModule,
            CoreModule,
        ),
    ),
    NavigationModuleProviding {
    override fun navigationRequestBuilder(): NavigationRequestBuilder = get()
    override fun navigationRequestResolver(): NavigationRequestResolver = get()
}
