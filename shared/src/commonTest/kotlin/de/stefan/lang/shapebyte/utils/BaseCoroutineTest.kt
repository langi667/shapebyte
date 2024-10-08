package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.di.CommonMainTestModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseCoroutineTest : BaseTest() {
    private val testDispatcher = StandardTestDispatcher()

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    fun test(block: suspend CoroutineScope.() -> Unit) = runTest {
        val testDispatcher = testDispatcher
        Dispatchers.setMain(testDispatcher)

        block()
    }
}

// TODO: separate file
abstract class BaseTest : KoinTest {
    // TODO: Improve this, WorkoutModule.testModule should not be in BaseTest
    private val testModules = CommonMainTestModules

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
