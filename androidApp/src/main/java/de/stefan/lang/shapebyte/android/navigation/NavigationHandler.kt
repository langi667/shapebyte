package de.stefan.lang.shapebyte.android.navigation

import androidx.navigation.NavHostController
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequest
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

class NavigationHandler(
    private val navHostController: NavHostController,
    override val logger: Logger = SharedModule.logger(),
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
