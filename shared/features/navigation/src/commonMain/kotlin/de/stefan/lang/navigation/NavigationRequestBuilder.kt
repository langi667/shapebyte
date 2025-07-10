package de.stefan.lang.navigation

import de.stefan.lang.navigation.NavigationParams.workoutIdParam

class NavigationRequestBuilder {
    fun quickWorkout(workoutId: Int): NavigationRequest.NavigateTo {
        return NavigationRequest.NavigateTo(
            NavigationRoute.QuickWorkout.pathFormat.replace(
                oldValue = "{$workoutIdParam}",
                newValue = workoutId.toString(),
            ),
        )
    }

    fun home(): NavigationRequest.NavigateTo {
        return NavigationRequest.NavigateTo(
            NavigationRoute.Home.pathFormat,
        )
    }
}
