package de.stefan.lang.shapebyte.android.navigation

import de.stefan.lang.navigation.NavigationRequest
import de.stefan.lang.shapebyte.android.navigation.NavParams.WorkoutIdParam

// TODO: check if that can be moved to shared and is reusable for iOS
enum class NavRoute(val path: String, val isStartDestination: Boolean) : NavigationRequest {
    HomeRoot("home", true),
    QuickWorkout("quickworkout/{$WorkoutIdParam}", false),
    ;

    companion object {
        val startDestination by lazy { NavRoute.entries.first { it.isStartDestination } }
    }
}
