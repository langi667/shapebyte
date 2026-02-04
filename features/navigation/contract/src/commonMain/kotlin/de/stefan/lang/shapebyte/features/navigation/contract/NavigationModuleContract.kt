package de.stefan.lang.shapebyte.features.navigation.contract

public interface NavigationModuleContract {
    public fun navigationRequestBuilder(): NavigationRequestBuilder
    public fun navigationRequestResolver(): NavigationRequestResolver
}
