package de.stefan.lang.shapebyte.featuretest

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProvidingTest.TestCoroutineScopeProvider
import de.stefan.lang.coretest.CoreTest
import de.stefan.lang.foundation.FoundationModule
import de.stefan.lang.shapebyte.featureCore.FeatureCoreModule
import de.stefan.lang.shapebyte.featureCore.platformdependencies.mocks.PackageDependencyProviderMock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class FeatureTest : CoreTest(), KoinTest {
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

    protected abstract val testModules: List<Module>

    private val requiredModules by lazy {
        CoreModule.testModule +
            FoundationModule.testModule +
            FeatureCoreModule.testModule
    }

    @BeforeTest
    fun startDI() {
        FeatureCoreModule.setup(platformDependencyProvider)
        startKoin {
            modules(requiredModules + testModules)
        }
    }

    @AfterTest
    fun stopDI() {
        stopKoin()
    }
}
