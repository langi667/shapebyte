package de.stefan.lang.coroutines.implementation.test

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

public object TestCoroutineContextProvider : CoroutineContextProvider {
    internal val scheduler: TestCoroutineScheduler = TestCoroutineScheduler()
    internal val testDispatcher: CoroutineDispatcher = StandardTestDispatcher(scheduler)

    override fun mainDispatcher(): CoroutineDispatcher = testDispatcher

    override fun mainImmediateDispatcher(): CoroutineDispatcher = testDispatcher

    override fun defaultDispatcher(): CoroutineDispatcher = testDispatcher

    override fun iODispatcher(): CoroutineDispatcher = testDispatcher
}
