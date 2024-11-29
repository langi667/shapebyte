package de.stefan.lang.shapebyte.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineContextProviding {
    fun mainDispatcher(): CoroutineDispatcher
    fun mainImmediateDispatcher(): CoroutineDispatcher
    fun defaultDispatcher(): CoroutineDispatcher
    fun iODispatcher(): CoroutineDispatcher
}
