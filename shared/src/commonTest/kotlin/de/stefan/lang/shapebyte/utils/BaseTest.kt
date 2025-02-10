package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.app.data.mocks.PackageDependencyProviderMock
import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.testcore.TestCoroutineContextProvider
import de.stefan.lang.testcore.TestCoroutineScopeProvider
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class BaseTest : KoinTest {
    private val platformDependencyProvider = PackageDependencyProviderMock(
        coroutineContextProvider = TestCoroutineContextProvider,
        coroutineScopeProviding = TestCoroutineScopeProvider,
    )

    private val testModules by lazy {
        DPI.setup(platformDependencyProvider)
        DPI.testModules
    }

    @BeforeTest
    fun startDI() {
        startKoin {
            modules(testModules)
        }
    }

    @AfterTest
    fun stopDI() {
        stopKoin()
    }
}
