package de.stefan.lang.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationTargetBuilderTest {
    @Test
    fun `quick workout route should be correct`() {
        val workoutId = 12345
        val navigationRouteBuilder =
            de.stefan.lang.shapebyte.features.navigation.api.NavigationRouteBuilder()
        assertEquals("quickworkout/$workoutId", navigationRouteBuilder.quickWorkoutRoute(workoutId))
    }
}
