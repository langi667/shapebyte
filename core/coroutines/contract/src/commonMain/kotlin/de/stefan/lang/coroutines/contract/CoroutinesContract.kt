package de.stefan.lang.coroutines.contract

public interface CoroutinesContract {
    public fun coroutineScopeProvider(): CoroutineScopeProviding
    public fun coroutineContextProvider(): CoroutineContextProviding
}
