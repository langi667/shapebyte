package de.stefan.lang.navigation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NavigationRouteIdTest : BaseNavigationTest() {
    @Test
    fun testDomain() {
        NavigationRouteId.entries.forEach {
            when (it) {
                NavigationRouteId.Home -> assertEquals("home", it.domain)
                NavigationRouteId.QuickWorkout -> assertEquals("quickworkout", it.domain)
            }
        }
    }

    @Test
    fun testRouteIdForDomain() {
        NavigationRouteId.entries.forEach {
            when (it) {
                NavigationRouteId.Home -> assertEquals(it, NavigationRouteId.routeIdForDomain("home"))
                NavigationRouteId.QuickWorkout -> assertEquals(it, NavigationRouteId.routeIdForDomain("quickworkout"))
            }
        }

        assertNull(NavigationRouteId.routeIdForDomain("nonexistingdomain"))
    }
}
