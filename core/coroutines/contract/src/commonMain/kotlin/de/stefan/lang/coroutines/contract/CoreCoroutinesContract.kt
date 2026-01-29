package de.stefan.lang.coroutines.contract

public interface CoreCoroutinesContract {
    public fun coroutineScopeProvider(): CoroutineScopeProviding
    public fun coroutineContextProvider(): CoroutineContextProviding
}
