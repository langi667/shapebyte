package de.stefan.lang.shapebyte.features.navigation.contract

interface NavigationModuleContract {
    fun navigationRequestBuilder(): NavigationRequestBuilder
    fun navigationRequestResolver(): NavigationRequestResolver
}
