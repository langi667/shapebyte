package de.stefan.lang.shapebyte.features.navigation.contract

public interface NavigationRequestHandler {
    public fun handleNavigationRequest(request: NavigationRequest): Unit
}
