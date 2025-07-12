package de.stefan.lang.shapebyte.android.navigation

import de.stefan.lang.shapebyte.features.navigation.api.NavigationRouteBuilder
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class NavigationTargetBuilderTest {
    @Test
    fun `quick workout route should be correct`() {
        val workoutId = 12345
        val navigationRouteBuilder =
            de.stefan.lang.shapebyte.features.navigation.api.NavigationRouteBuilder()
        assertEquals("quickworkout/$workoutId", navigationRouteBuilder.quickWorkoutRoute(workoutId))
    }
}
