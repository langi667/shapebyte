package de.stefan.lang.shapebyte.features.navigation.contract

enum class NavigationRoute(val id: String, val pathFormat: String) {
    Home(
        id = NavigationRouteId.Home.domain,
        pathFormat = NavigationRouteId.Home.domain,
    ),

    QuickWorkout(
        id = NavigationRouteId.QuickWorkout.domain,
        pathFormat = "${NavigationRouteId.QuickWorkout.domain}/{${NavigationParams.workoutIdParam}}",
    ),
}
