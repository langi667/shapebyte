package de.stefan.lang.navigation

// TODO: may not be needed
class NavigationRouteBuilder {
    fun quickWorkoutRoute(workoutId: Int): String {
        // TODO: depending on type ...
        return NavigationRoute.QuickWorkout.pathFormat.replace(
            "{${NavigationParams.workoutIdParam}}",
            workoutId.toString(),
        )
    }
}
