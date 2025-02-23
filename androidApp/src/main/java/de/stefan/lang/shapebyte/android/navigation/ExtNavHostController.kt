package de.stefan.lang.shapebyte.android.navigation

import androidx.navigation.NavHostController
import de.stefan.lang.shapebyte.features.workout.workoutData.Workout

fun NavHostController.navigateToQuickWorkouts(workout: Workout) {
    val path = NavRouteBuilder.quickWorkoutRoute(workout.id)
    this.navigate(path)
}
