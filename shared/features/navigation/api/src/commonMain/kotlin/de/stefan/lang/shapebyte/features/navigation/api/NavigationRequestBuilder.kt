package de.stefan.lang.shapebyte.features.navigation.api

import de.stefan.lang.shapebyte.features.navigation.api.NavigationParams.workoutIdParam

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
