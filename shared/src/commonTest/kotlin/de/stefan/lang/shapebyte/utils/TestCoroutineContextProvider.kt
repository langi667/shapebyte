package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

object TestCoroutineContextProvider : CoroutineContextProviding {
    private val testDispatcher = StandardTestDispatcher()

    override fun mainDispatcher(): CoroutineDispatcher = testDispatcher
    override fun mainImmediateDispatcher(): CoroutineDispatcher = testDispatcher
    override fun defaultDispatcher(): CoroutineDispatcher = testDispatcher
    override fun iODispatcher(): CoroutineDispatcher = testDispatcher
}
