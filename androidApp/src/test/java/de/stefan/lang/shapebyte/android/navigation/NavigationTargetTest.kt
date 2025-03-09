package de.stefan.lang.shapebyte.android.navigation

import de.stefan.lang.navigation.NavigationTarget
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationTargetTest {
    @Test
    fun `test nav routes`() {
        assertEquals("home", NavigationTarget.HomeRoot.path)
        assertEquals("quickworkout/{workoutId}", NavigationTarget.QuickWorkout.pathFormat)
    }

    @Test
    fun `test start navigation`() {
        assertEquals(NavigationTarget.HomeRoot, NavigationTarget.startDestination)
    }
}
