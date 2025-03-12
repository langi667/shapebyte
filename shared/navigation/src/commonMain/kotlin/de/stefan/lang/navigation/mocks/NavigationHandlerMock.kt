package de.stefan.lang.navigation.mocks

import de.stefan.lang.navigation.NavigationRequest
import de.stefan.lang.navigation.NavigationRequestHandling

class NavigationHandlerMock : NavigationRequestHandling {
    override fun handleNavigationRequest(request: NavigationRequest) {
        // no-op
    }
}
