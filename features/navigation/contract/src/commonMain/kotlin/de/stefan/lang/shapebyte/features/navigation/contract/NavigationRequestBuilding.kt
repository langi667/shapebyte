package de.stefan.lang.shapebyte.features.navigation.contract

interface NavigationRequestBuilding {
    fun quickWorkout(workoutId: Int): NavigationRequest.NavigateTo
    fun home(): NavigationRequest.NavigateTo
}
