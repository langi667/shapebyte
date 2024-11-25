package de.stefan.lang.shapebyte.app

import app.cash.turbine.test
import de.stefan.lang.shapebyte.utils.BaseCoroutineTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AppInitializerTest : BaseCoroutineTest() {
    @Test
    fun `initial state`() = test {
        assertEquals(AppInitializer.State.UNINITIALIZED, AppInitializer.state.value)
    }

    @Test
    fun `should emit correct states`() = test {
        AppInitializer.initialize().test {
            assertEquals(AppInitializer.State.INITIALIZING, awaitItem())
            assertEquals(AppInitializer.State.INITIALIZED, awaitItem())
            expectNoEvents()
        }
    }
}
