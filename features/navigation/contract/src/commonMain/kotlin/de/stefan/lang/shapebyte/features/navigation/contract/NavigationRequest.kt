package de.stefan.lang.shapebyte.features.navigation.contract

public sealed class NavigationRequest {
    public data object Back : NavigationRequest()
    public data class NavigateTo(public val path: String) : NavigationRequest()
}
