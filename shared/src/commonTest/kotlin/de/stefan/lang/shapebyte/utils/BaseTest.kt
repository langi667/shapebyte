package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.app.data.PlatformDependencyProviding
import de.stefan.lang.shapebyte.di.DPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class BaseTest : KoinTest {
    private val platformDependencyProvider = object : PlatformDependencyProviding {
        override val coroutineContextProvider = TestCoroutineContextProvider
        override val coroutineScopeProviding = TestCoroutineScopeProvider
    }

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
