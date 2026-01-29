package de.stefan.lang.shapebyte.features.navigation.contract

sealed class NavigationRequest {
    data object Back : NavigationRequest()
    data class NavigateTo(val path: String) : NavigationRequest()
}
