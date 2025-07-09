package de.stefan.lang.coreCoroutinesProviding

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class CoroutineContextProvider : CoroutineContextProviding {
    override fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main
    override fun mainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
    override fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    override fun iODispatcher() = Dispatchers.IO
}
