package de.stefan.lang.shapebyte.android.navigation

import androidx.navigation.NavHostController
import de.stefan.lang.coreutils.api.Loggable
import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.navigation.NavigationRequest
import de.stefan.lang.navigation.NavigationRequestHandling
import de.stefan.lang.shapebyte.SharedModule

class NavigationHandler(
    private val navHostController: NavHostController,
    override val logger: Logging = SharedModule.logger(),
) : NavigationRequestHandling, Loggable {

    override fun handleNavigationRequest(request: NavigationRequest) {
        when (request) {
            is NavigationRequest.Back -> navHostController.navigateUp()
            is NavigationRequest.NavigateTo -> navHostController.navigate(
                request.path,
            )
            else -> logE("Unknown navigation request: $request")
        }
    }
}
