package de.stefan.lang.shapebyte.features.navigation.contract

public enum class NavigationRoute(public val id: String, public val pathFormat: String) {
    Home(
        id = NavigationRouteId.Home.domain,
        pathFormat = NavigationRouteId.Home.domain,
    ),

    QuickWorkout(
        id = NavigationRouteId.QuickWorkout.domain,
        pathFormat = "${NavigationRouteId.QuickWorkout.domain}/{${NavigationParams.workoutIdParam}}",
    ),
}
