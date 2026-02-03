package de.stefan.lang.coroutines.contract

import kotlinx.coroutines.CoroutineDispatcher

public interface CoroutineContextProvider {
    public fun mainDispatcher(): CoroutineDispatcher
    public fun mainImmediateDispatcher(): CoroutineDispatcher
    public fun defaultDispatcher(): CoroutineDispatcher
    public fun iODispatcher(): CoroutineDispatcher
}
