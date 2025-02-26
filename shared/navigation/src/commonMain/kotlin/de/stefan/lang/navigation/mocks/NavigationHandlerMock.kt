package de.stefan.lang.navigation.mocks

import de.stefan.lang.navigation.NavigationHandling
import de.stefan.lang.navigation.NavigationRequest

class NavigationHandlerMock : NavigationHandling {
    override fun handleNavigationRequest(request: NavigationRequest) {
        // no-op
    }
}
