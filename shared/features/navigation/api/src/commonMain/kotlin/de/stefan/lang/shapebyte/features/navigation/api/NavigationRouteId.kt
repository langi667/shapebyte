package de.stefan.lang.shapebyte.features.navigation.api

enum class NavigationRouteId {
    Home,
    QuickWorkout,
    ;

    val domain: String
        get() = name
            .lowercase()
            .replace("_", "")
            .replace("-", "")
            .trim()

    companion object {
        fun routeIdForDomain(domain: String): NavigationRouteId? = NavigationRouteId
            .entries
            .firstOrNull { it.domain == domain }
    }
}
