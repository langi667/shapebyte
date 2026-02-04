package de.stefan.lang.shapebyte.features.navigation.contract

public sealed class NavigationTarget {
    public data object Back : NavigationTarget()
    public data object Home : NavigationTarget()

    public data class QuickWorkout(public val workoutId: Int) : NavigationTarget()
}
