package de.stefan.lang.shapebyte.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseCoroutineTest : BaseTest() {
    private val testDispatcher = StandardTestDispatcher()
    protected val testScope = TestScope(testDispatcher)

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    fun test(block: suspend CoroutineScope.() -> Unit) {
        Dispatchers.setMain(testDispatcher)
        runTest {
            block()
        }
    }
}
