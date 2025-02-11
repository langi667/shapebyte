package de.stefan.lang.shapebyte.utils

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProvidingTest.TestCoroutineScopeProvider
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundationCore.platform.mocks.PackageDependencyProviderMock
import de.stefan.lang.shapebyte.SharedModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class SharedComponentTest : CoreTest(), KoinTest {
    private class CoroutineContextProvider(val testDispatcher: TestDispatcher) : CoroutineContextProviding {
        override fun mainDispatcher(): CoroutineDispatcher = testDispatcher
        override fun mainImmediateDispatcher(): CoroutineDispatcher = testDispatcher
        override fun defaultDispatcher(): CoroutineDispatcher = testDispatcher
        override fun iODispatcher(): CoroutineDispatcher = testDispatcher
    }

    private val platformDependencyProvider = PackageDependencyProviderMock(
        coroutineContextProvider = CoroutineContextProvider(testDispatcher),
        coroutineScopeProviding = TestCoroutineScopeProvider,
    )

    private val testModules by lazy {
        SharedModule.setup(platformDependencyProvider)
        SharedModule.testModules
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
