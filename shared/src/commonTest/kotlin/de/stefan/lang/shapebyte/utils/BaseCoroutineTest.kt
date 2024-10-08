package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.features.workout.di.workoutTestModule
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
        // testDispatcher.cleanupTestCoroutines()
    }

    fun test(block: suspend CoroutineScope.() -> Unit) = runTest {
        val testDispatcher = testDispatcher
        Dispatchers.setMain(testDispatcher)

        block()
    }
}

abstract class BaseTest : KoinTest {
    private val testModules = testUtilsModule + workoutTestModule

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
