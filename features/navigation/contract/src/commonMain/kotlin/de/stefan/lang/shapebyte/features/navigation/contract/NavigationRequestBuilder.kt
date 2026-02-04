package de.stefan.lang.shapebyte.features.navigation.contract

public interface NavigationRequestBuilder {
    public fun quickWorkout(workoutId: Int): NavigationRequest.NavigateTo
    public fun home(): NavigationRequest.NavigateTo
}
