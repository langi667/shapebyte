package de.stefan.lang.coroutines.contract

public interface CoreCoroutinesContract {
    public fun coroutineScopeProvider(): CoroutineScopeProvider
    public fun coroutineContextProvider(): CoroutineContextProvider
}
