package de.stefan.lang.shapebyte.features.navigation.contract

import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestResolver

interface NavigationModuleContract {
    fun navigationRequestBuilder(): NavigationRequestBuilder
    fun navigationRequestResolver(): NavigationRequestResolver
}