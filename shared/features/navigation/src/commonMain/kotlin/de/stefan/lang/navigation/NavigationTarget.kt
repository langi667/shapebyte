package de.stefan.lang.navigation

sealed class NavigationTarget {
    data object Back : NavigationTarget()
    data object Home : NavigationTarget()

    data class QuickWorkout(val workoutId: Int) : NavigationTarget()
}
