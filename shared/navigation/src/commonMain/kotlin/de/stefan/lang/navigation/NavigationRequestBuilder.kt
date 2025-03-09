package de.stefan.lang.navigation

import de.stefan.lang.navigation.NavigationParams.workoutIdParam

// TODO: Test
object NavigationRequestBuilder {
    fun quickWorkout(workoutId: Int): NavigationRequest.NavigateTo {
        return NavigationRequest.NavigateTo(
            NavigationRoute.QuickWorkout.pathFormat.replace(
                oldValue = "{$workoutIdParam}",
                newValue = workoutId.toString()
            )
        )
    }
}

