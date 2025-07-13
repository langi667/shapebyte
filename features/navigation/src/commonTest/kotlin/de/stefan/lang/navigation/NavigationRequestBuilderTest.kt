package de.stefan.lang.navigation

import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequest
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRequestBuilder
import de.stefan.lang.shapebyte.features.navigation.api.NavigationRoute
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationRequestBuilderTest : BaseNavigationTest() {
    @Test
    fun testQuickWorkout() {
        val workoutId = 1
        val sut = createSUT()
        val result = sut.quickWorkout(workoutId)

        assertEquals(
            NavigationRequest.NavigateTo("${NavigationRoute.QuickWorkout.id}/$workoutId"),
            result,
        )
    }

    @Test
    fun testHome() {
        val sut = createSUT()
        val result = sut.home()

        assertEquals(
            NavigationRequest.NavigateTo(NavigationRoute.Home.id),
            result,
        )
    }

    private fun createSUT(): NavigationRequestBuilder {
        return NavigationModule.navigationRequestBuilder()
    }
}
