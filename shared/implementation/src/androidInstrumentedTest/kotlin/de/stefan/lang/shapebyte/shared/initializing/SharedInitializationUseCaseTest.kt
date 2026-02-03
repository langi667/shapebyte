package de.stefan.lang.shapebyte.shared.initializing

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundation.core.contract.FoundationCoreContract
import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.app.AppInfoProvider
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import de.stefan.lang.foundation.core.fake.app.AppInfoProviderFake
import de.stefan.lang.foundationCore.FoundationCoreModule
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

@RunWith(AndroidJUnit4::class)
class SharedInitializationUseCaseTest: CoreTest() {

    override val testModules: List<Module>
        get() = super.testModules +
                CoroutinesModule.testDiModule +
                FoundationCoreModule.testDiModule

    @Test
    @DisplayName("test initial state")
    fun initialState() = test {
        val sut = createSUT()
        TestCase.assertEquals(AppInitializationState.UNINITIALIZED, sut.state)
        TestCase.assertFalse(sut.isInitialized)

        sut.flow.test {
            TestCase.assertEquals(AppInitializationState.UNINITIALIZED, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    @DisplayName("tests app initializing process and respective calls")
    fun testInitializing() = test {
        val sut = createSUT()


        TestCase.assertEquals(AppInitializationState.UNINITIALIZED, sut.state)
        TestCase.assertFalse(sut.isInitialized)

        sut.flow.test {
            TestCase.assertEquals(AppInitializationState.UNINITIALIZED, awaitItem())
            expectNoEvents()
        }

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val platformDependencies = PlatformDependencyProvider(
            applicationContext = appContext,
            appInfo = AppInfoProviderFake().appInfo(),
            appContextProvider = ContextProvider(appContext),
            appResourceProvider = AppResourceProvider(
                null
            ),
        )

        stopKoin() // needs to be called because koin from the test case is already running
        sut.invoke(platformDependencies, testModules)

        sut.flow.test {
            TestCase.assertEquals(AppInitializationState.INITIALIZING, awaitItem())
            TestCase.assertEquals(AppInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }

        TestCase.assertTrue(sut.isInitialized)

        // second call of invoke should emit INITIALIZED immediately
        sut.invoke(platformDependencies, testModules)

        sut.flow.test {
            TestCase.assertEquals(AppInitializationState.INITIALIZED, awaitItem())
            expectNoEvents()
        }
    }

    private fun createSUT(): SharedInitializationUseCase = SharedInitializationUseCase(
        coroutineContextProvider = { CoroutinesModule.coroutineContextProvider() },
        coroutineScopeProvider = { CoroutinesModule.coroutineScopeProvider() },
        deviceInfoProvider = { FoundationCoreModule.deviceInfoProvider() },
        appInfoProvider = { AppInfoProviderFake() },
    )
}