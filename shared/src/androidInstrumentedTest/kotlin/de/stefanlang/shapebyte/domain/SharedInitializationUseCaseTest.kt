package de.stefanlang.shapebyte.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import de.stefan.lang.coreCoroutinesProvidingTest.TestCoroutineContextProvider
import de.stefan.lang.coreCoroutinesProvidingTest.TestCoroutineScopeProvider
import de.stefan.lang.foundationCore.platform.PlatformDependencyProvider
import de.stefan.lang.shapebyte.initializing.SharedInitializationState
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SharedInitializationUseCaseTest: CoreTest() {

    @Test
    @DisplayName("test initial state")
    fun initialState() = test {
        val sut = createSUT()
        assertEquals(SharedInitializationState.UNINITIALIZED, sut.state)
        assertFalse(sut.isInitialized)

        sut.flow.test {
            assertEquals(SharedInitializationState.UNINITIALIZED, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    @DisplayName("tests app initializing process and respective calls")
    fun testInitializing() = test {
        val sut = createSUT()

        mockkObject(SharedModule)
        assertEquals(SharedInitializationState.UNINITIALIZED, sut.state)
        assertFalse(sut.isInitialized)

        sut.flow.test {
            assertEquals(SharedInitializationState.UNINITIALIZED, awaitItem())
            expectNoEvents()
        }

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val platformDependencies = PlatformDependencyProvider(
            applicationContext = appContext,
            coroutineScopeProviding = TestCoroutineScopeProvider,
            coroutineContextProvider = TestCoroutineContextProvider,
            appInfo = AppInfo(
                packageName = "de.stefan.lang.shapebyte",
                versionName = "1.0",
                versionCode = 0,
                debugMode = true
            ),
            appContextProvider = ContextProvider(appContext),
            appResourceProvider = AppResourceProvider(null),
        )

        sut.invoke(platformDependencies)

        sut.flow.test {
            assertEquals(SharedInitializationState.INITIALIZING, awaitItem())
            assertEquals(SharedInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }

        verify(exactly = 1) { SharedModule.start(platformDependencies) }
        verify(exactly = 1) { SharedModule.deviceInfoProvider() }

        assertTrue(sut.isInitialized)

        // second call of invoke should emit INITIALIZED immediately
        sut.invoke(platformDependencies)

        sut.flow.test {
            assertEquals(SharedInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }

        unmockkObject(SharedModule)
    }

    private fun createSUT(): SharedInitializationUseCase = SharedModule.sharedInitializationUseCase()
}