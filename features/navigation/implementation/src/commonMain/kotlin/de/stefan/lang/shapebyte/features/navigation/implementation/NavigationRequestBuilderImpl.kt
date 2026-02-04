package de.stefan.lang.shapebyte.features.navigation.implementation

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationParams.workoutIdParam
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequest
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRoute

public class NavigationRequestBuilderImpl : NavigationRequestBuilder {
    override fun quickWorkout(workoutId: Int): NavigationRequest.NavigateTo {
        return NavigationRequest.NavigateTo(
            NavigationRoute.QuickWorkout.pathFormat.replace(
                oldValue = "{$workoutIdParam}",
                newValue = workoutId.toString(),
            ),
        )
    }

    override fun home(): NavigationRequest.NavigateTo {
        return NavigationRequest.NavigateTo(
            NavigationRoute.Home.pathFormat,
        )
    }
}
