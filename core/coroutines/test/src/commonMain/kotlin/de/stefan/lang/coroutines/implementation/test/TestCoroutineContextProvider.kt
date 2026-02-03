package de.stefan.lang.coroutines.implementation.test

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

public object TestCoroutineContextProvider : CoroutineContextProvider {
    private val testDispatcher = StandardTestDispatcher()

    override fun mainDispatcher(): CoroutineDispatcher = testDispatcher
    override fun mainImmediateDispatcher(): CoroutineDispatcher = testDispatcher
    override fun defaultDispatcher(): CoroutineDispatcher = testDispatcher
    override fun iODispatcher(): CoroutineDispatcher = testDispatcher
}
