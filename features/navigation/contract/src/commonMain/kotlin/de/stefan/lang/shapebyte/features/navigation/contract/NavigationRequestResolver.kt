package de.stefan.lang.shapebyte.features.navigation.contract

public interface NavigationRequestResolver {
    public fun resolve(request: NavigationRequest): NavigationTarget
}
