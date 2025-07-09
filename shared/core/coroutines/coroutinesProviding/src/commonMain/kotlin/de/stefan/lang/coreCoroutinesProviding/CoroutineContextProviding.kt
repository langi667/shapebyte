package de.stefan.lang.coreCoroutinesProviding

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineContextProviding {
    fun mainDispatcher(): CoroutineDispatcher
    fun mainImmediateDispatcher(): CoroutineDispatcher
    fun defaultDispatcher(): CoroutineDispatcher
    fun iODispatcher(): CoroutineDispatcher
}
