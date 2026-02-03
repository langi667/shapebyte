package de.stefan.lang.shapebyte.features.navigation.contract

interface NavigationRequestResolver {
    fun resolve(request: NavigationRequest): NavigationTarget
}
