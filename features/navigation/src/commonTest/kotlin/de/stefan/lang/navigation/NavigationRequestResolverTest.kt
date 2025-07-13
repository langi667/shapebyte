package de.stefan.lang.navigation

import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequest
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestResolver
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRoute
import de.stefan.lang.shapebyte.features.navigation.api.NavigationTarget
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationRequestResolverTest : BaseNavigationTest() {
    @Test
    fun resolveBack() {
        val result = createSUT().resolve(NavigationRequest.Back)
        assertEquals(NavigationTarget.Back, result)
    }

    @Test
    fun resolveHome() {
        val result = createSUT().resolve(NavigationRequest.NavigateTo(NavigationRoute.Home.id))
        assertEquals(NavigationTarget.Home, result)
    }

    private fun createSUT(): NavigationRequestResolver {
        return NavigationModule.navigationRequestResolver()
    }
}
