package de.stefan.lang.coroutines.contract

interface Contract {
    public fun coroutineScopeProvider(): CoroutineScopeProviding
    public fun coroutineContextProvider(): CoroutineContextProviding
}