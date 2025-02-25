package de.stefan.lang.shapebyte.android.navigation

import androidx.navigation.NavHostController
import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.navigation.NavigationHandling
import de.stefan.lang.navigation.NavigationRequest
import de.stefan.lang.navigation.NavigationRequestBack
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.features.home.QuickWorkoutNavigationRequest

class NavigationHandler(
    private val navHostController: NavHostController,
    private val navRouteBuilder: NavRouteBuilder = NavRouteBuilder(),
    override val logger: Logging = SharedModule.logger()
): NavigationHandling, Loggable {
    override fun handleNavigationRequest(request: NavigationRequest){
        when (request) {
            is NavigationRequestBack -> navHostController.navigateUp()
            is QuickWorkoutNavigationRequest -> navHostController.navigate(navRouteBuilder.quickWorkoutRoute(request.workoutId))
            else -> logE("Unknown navigation request: $request")
        }
    }
}