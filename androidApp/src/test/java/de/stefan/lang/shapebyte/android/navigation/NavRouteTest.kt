package de.stefan.lang.shapebyte.android.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class NavRouteTest {
    @Test
    fun `test nav routes`() {
        assertEquals("home", NavRoute.HomeRoot.path)
        assertEquals("quickworkout/{workoutId}", NavRoute.QuickWorkout.path)
    }

    @Test
    fun `test start navigation`() {
        assertEquals(NavRoute.HomeRoot, NavRoute.startDestination)
    }
}
