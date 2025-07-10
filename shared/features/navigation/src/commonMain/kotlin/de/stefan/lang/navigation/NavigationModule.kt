package de.stefan.lang.navigation

import de.stefan.lang.core.di.DIModuleDeclaration
import org.koin.core.component.get

interface NavigationModuleProviding {
    fun navigationRequestBuilder(): NavigationRequestBuilder
    fun navigationRequestResolver(): NavigationRequestResolver
}

object NavigationModule :
    DIModuleDeclaration(
        allEnvironments = {
            single { NavigationRequestBuilder() }
            single { NavigationRequestResolver() }
        },
    ),
    NavigationModuleProviding {
    override fun navigationRequestBuilder(): NavigationRequestBuilder = get()
    override fun navigationRequestResolver(): NavigationRequestResolver = get()
}
