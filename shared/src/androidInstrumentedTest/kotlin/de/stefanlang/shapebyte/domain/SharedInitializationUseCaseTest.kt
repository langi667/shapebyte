package de.stefanlang.shapebyte.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.shapebyte.initializing.AppInitializationState
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class SharedInitializationUseCaseTest: CoreTest() {
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

        mockkObject(SharedModule)
        val appInfo = AppInfo(
            packageName = "de.stefan.lang.shapebyte",
            versionName = "1.0",
            versionCode = 0,
            debugMode = false // Hack: if true, it would start strict mode, maybe add a mode flag, like: Test, ...
        )

        every {SharedModule.appInfo()} returns appInfo

        assertEquals(AppInitializationState.UNINITIALIZED, sut.state)
        assertFalse(sut.isInitialized)

        sut.flow.test {
            assertEquals(AppInitializationState.UNINITIALIZED, awaitItem())
            expectNoEvents()
        }

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val platformDependencies = PlatformDependencyProvider(
            applicationContext = appContext,
            appInfo = appInfo,
            appContextProvider = ContextProvider(appContext),
            appResourceProvider = AppResourceProvider(
                null
            ),
        )

        stopKoin() // needs to be called because koin from the test case is already running
        sut.invoke(platformDependencies, SharedModule.testModules)

        sut.flow.test {
            assertEquals(AppInitializationState.INITIALIZING, awaitItem())
            assertEquals(AppInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }

        verify(exactly = 1) { SharedModule.deviceInfoProvider() }

        assertTrue(sut.isInitialized)

        // second call of invoke should emit INITIALIZED immediately
        sut.invoke(platformDependencies, SharedModule.testModules)

        sut.flow.test {
            assertEquals(AppInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }

        unmockkObject(SharedModule)
    }

    private fun createSUT(): SharedInitializationUseCase = SharedModule.sharedInitializationUseCase()
}