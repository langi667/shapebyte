package de.stefan.lang.shapebyte.android.navigation

// TODO: maybe use case, since we might wanna check feature toggles ...
object NavRouteBuilder {
    fun quickWorkoutRoute(workoutId: Int): String {
        // TODO: depending on type ...
        return NavRoute.QuickWorkout.path.replace(
            "{${NavParams.WorkoutIdParam}}",
            workoutId.toString(),
        )
    }
}
