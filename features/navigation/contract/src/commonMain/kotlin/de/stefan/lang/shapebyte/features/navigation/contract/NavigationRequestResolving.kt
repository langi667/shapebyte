package de.stefan.lang.shapebyte.features.navigation.contract

interface NavigationRequestResolving {
    fun resolve(request: NavigationRequest): NavigationTarget
}
