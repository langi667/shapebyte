package de.stefan.lang.shapebyte.features.navigation.contract

interface NavigationRequestBuilder {
    fun quickWorkout(workoutId: Int): NavigationRequest.NavigateTo
    fun home(): NavigationRequest.NavigateTo
}
