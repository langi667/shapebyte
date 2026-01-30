package de.stefan.lang.shapebyte.features.navigation.contract

interface NavigationModuleContract {
    fun navigationRequestBuilder(): NavigationRequestBuilding
    fun navigationRequestResolver(): NavigationRequestResolving
}
