package de.stefan.lang.shapebyte.android.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.stefan.lang.navigation.NavigationRoute
import de.stefan.lang.shapebyte.android.features.home.ui.HomeRootView
import de.stefan.lang.shapebyte.android.features.workout.timed.ui.TimedWorkoutView

@Composable
fun NavigationView(
    modifier: Modifier = Modifier,
    startRoute: NavigationRoute = NavigationRoute.Home,
) {
    val startDestination = startRoute.pathFormat
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        NavigationRoute.entries.forEach {
            when (it) {
                NavigationRoute.Home -> {
                    composable(it.pathFormat) {
                        HomeRootView(navController, modifier)
                    }
                }

                NavigationRoute.QuickWorkout -> {
                    composable(it.pathFormat) { backStackEntry ->
                        val itemId = backStackEntry.arguments.workoutIdOr("-1").toInt()
                        TimedWorkoutView(itemId, navController, modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}
