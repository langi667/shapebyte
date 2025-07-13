package de.stefan.lang.coroutines.api

import kotlinx.coroutines.CoroutineDispatcher

public interface CoroutineContextProviding {
    fun mainDispatcher(): CoroutineDispatcher
    fun mainImmediateDispatcher(): CoroutineDispatcher
    fun defaultDispatcher(): CoroutineDispatcher
    fun iODispatcher(): CoroutineDispatcher
}
