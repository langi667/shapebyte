package de.stefan.lang.shapebyte.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseCoroutineTest {
    private val testDispatcher = StandardTestDispatcher()

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        // testDispatcher.cleanupTestCoroutines()
    }

    fun test(block: suspend CoroutineScope.() -> Unit) = runTest {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        block()
    }
}
