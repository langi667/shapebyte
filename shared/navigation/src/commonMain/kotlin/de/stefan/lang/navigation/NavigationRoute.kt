package de.stefan.lang.navigation

object NavigationRouteId {
    const val HOME: String = "home"
    const val QUICK_WORKOUT: String = "quickworkout"
}
enum class NavigationRoute(val id: String, val pathFormat: String) {
    Home(NavigationRouteId.HOME, NavigationRouteId.HOME),
    QuickWorkout(NavigationRouteId.QUICK_WORKOUT, "${NavigationRouteId.QUICK_WORKOUT}/{${NavigationParams.workoutIdParam}}"),

    ;
}

