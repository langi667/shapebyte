package de.stefan.lang.navigation

enum class NavigationRouteId {
    Home,
    QuickWorkout,
    ;

    // TODO: test
    val domain: String
        get() = name
            .lowercase()
            .replace("_", "")
            .replace("-", "")
            .trim()

    companion object {
        // TODO: test
        fun routeIdForDomain(domain: String): NavigationRouteId? = NavigationRouteId
            .entries
            .firstOrNull { it.domain == domain }
    }
}
