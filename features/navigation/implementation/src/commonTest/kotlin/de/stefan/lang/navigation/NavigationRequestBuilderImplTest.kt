package de.stefan.lang.navigation

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequest
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRoute
import de.stefan.lang.shapebyte.features.navigation.contract.impl.NavigationRequestBuilderImpl
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationRequestBuilderImplTest : BaseNavigationTest() {
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

    private fun createSUT(): NavigationRequestBuilderImpl {
        return NavigationRequestBuilderImpl()
    }
}
