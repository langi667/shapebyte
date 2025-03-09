package de.stefan.lang.navigation

sealed class NavigationRequest {
    data object Back: NavigationRequest()
    data class NavigateTo(val path: String): NavigationRequest()
}





