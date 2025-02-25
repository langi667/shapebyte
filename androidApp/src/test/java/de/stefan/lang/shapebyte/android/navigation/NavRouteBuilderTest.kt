package de.stefan.lang.shapebyte.android.navigation

import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class NavRouteBuilderTest {
    @Test
    fun `quick workout route should be correct`() {
        val workoutId = 12345
        val navRouteBuilder = NavRouteBuilder()
        assertEquals("quickworkout/$workoutId", navRouteBuilder.quickWorkoutRoute(workoutId))
    }
}
