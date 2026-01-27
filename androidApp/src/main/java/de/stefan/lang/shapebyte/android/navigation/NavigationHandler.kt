package de.stefan.lang.shapebyte.android.navigation

import androidx.navigation.NavHostController
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequest
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestHandling
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging

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
