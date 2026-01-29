package de.stefan.lang.shapebyte.features.navigation.contract

sealed class NavigationTarget {
    data object Back : NavigationTarget()
    data object Home : NavigationTarget()

    data class QuickWorkout(val workoutId: Int) : NavigationTarget()
}
