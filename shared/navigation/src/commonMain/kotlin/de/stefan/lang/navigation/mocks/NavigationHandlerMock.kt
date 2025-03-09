package de.stefan.lang.navigation.mocks

import de.stefan.lang.navigation.NavigationRequestHandling
import de.stefan.lang.navigation.NavigationRequest

class NavigationHandlerMock : NavigationRequestHandling {
    override fun handleNavigationRequest(request: NavigationRequest) {
        // no-op
    }
}
