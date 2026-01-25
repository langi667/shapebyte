package de.stefan.lang.coroutines.implementation

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class CoroutineContextProvider : CoroutineContextProviding {
    override fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main
    override fun mainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
    override fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    override fun iODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
