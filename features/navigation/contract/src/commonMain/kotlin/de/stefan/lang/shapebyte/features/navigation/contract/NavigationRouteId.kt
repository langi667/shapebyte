package de.stefan.lang.shapebyte.features.navigation.contract

public enum class NavigationRouteId {
    Home,
    QuickWorkout,
    ;

    public val domain: String
        get() = name
            .lowercase()
            .replace("_", "")
            .replace("-", "")
            .trim()

    public companion object {
        public fun routeIdForDomain(domain: String): NavigationRouteId? = NavigationRouteId
            .entries
            .firstOrNull { it.domain == domain }
    }
}
