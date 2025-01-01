package de.stefanlang.shapebyte.domain


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import de.stefan.lang.shapebyte.app.domain.AppInitializationUseCase
import de.stefan.lang.shapebyte.app.domain.AppInitializationState
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class AppInitializationUseCaseTest {
    //  TODO: remove once BaseCoroutineTest is ported to a separate module, see SB-65
    private val testDispatcher = StandardTestDispatcher()

    @Test
    @DisplayName("test initial state")
    fun initialState() = test {
        val sut = createSUT()
        assertEquals(AppInitializationState.UNINITIALIZED, sut.state)
        assertFalse(sut.isInitialized)

        sut.flow.test {
            assertEquals(AppInitializationState.UNINITIALIZED, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    @DisplayName("tests app initializing process and respective calls")
    fun testInitializing() = test {
        val sut = createSUT()

        mockkObject(DPI)
        assertEquals(AppInitializationState.UNINITIALIZED, sut.state)
        assertFalse(sut.isInitialized)

        sut.flow.test {
            assertEquals(AppInitializationState.UNINITIALIZED, awaitItem())
            expectNoEvents()
        }

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // TODO: must come from separate module, see SB-65
        val testCoroutineScopeProvider = object : CoroutineScopeProviding {
            override fun createCoroutineScope(context: CoroutineContext): CoroutineScope {
                return TestScope(context)
            }
        }

        // TODO: must come from separate module, see SB-65
        val testCoroutineContextProvider = object : CoroutineContextProviding {
            override fun mainDispatcher(): CoroutineDispatcher = testDispatcher
            override fun mainImmediateDispatcher(): CoroutineDispatcher = testDispatcher
            override fun defaultDispatcher(): CoroutineDispatcher = testDispatcher
            override fun iODispatcher(): CoroutineDispatcher = testDispatcher
        }

        val platformDependencies = PlatformDependencyProvider(
            applicationContext = appContext,
            coroutineScopeProviding = testCoroutineScopeProvider,
            coroutineContextProvider = testCoroutineContextProvider
        )

        sut.invoke(platformDependencies)

        sut.flow.test {
            assertEquals(AppInitializationState.INITIALIZING, awaitItem())
            assertEquals(AppInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }

        verify(exactly = 1) { DPI.start(platformDependencies) }
        verify(exactly = 1) { DPI.deviceInfoProvider() }

        assertTrue(sut.isInitialized)

        // second call of invoke should emit INITIALIZED immediately
        sut.invoke(platformDependencies)

        sut.flow.test {
            assertEquals(AppInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }

        unmockkObject(DPI)
    }

    //  TODO: remove once BaseCoroutineTest is ported to a separate module, see SB-65
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //  TODO: remove once BaseCoroutineTest is ported to a separate module, see SB-65
    fun test(block: suspend CoroutineScope.() -> Unit) {
        Dispatchers.setMain(testDispatcher)
        runTest {
            block()
        }
    }

    private fun createSUT(): AppInitializationUseCase = DPI.appInitializerUseCase()
}