package de.stefan.lang.coroutines

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.coroutines.contract.CoreCoroutinesContract
import de.stefan.lang.coroutines.implementation.CoroutineContextProvider
import de.stefan.lang.coroutines.implementation.CoroutineScopeProvider
import de.stefan.lang.coroutines.implementation.test.TestCoroutineContextProvider
import de.stefan.lang.coroutines.implementation.test.TestCoroutineScopeProvider
import org.koin.core.component.get

public object CoreCoroutinesModule :
    ModuleBindings(
        allEnvironments = { },
        appEnvironmentOnly = {
            single<CoroutineScopeProviding> { CoroutineScopeProvider() }
            single<CoroutineContextProviding> { CoroutineContextProvider() }
        },
        testEnvironmentOnly = {
            single<CoroutineScopeProviding> {
                TestCoroutineScopeProvider
            }
            single<CoroutineContextProviding> {
                TestCoroutineContextProvider
            }
        },
    ),
    CoreCoroutinesContract {
    public override fun coroutineScopeProvider(): CoroutineScopeProviding = get()
    public override fun coroutineContextProvider(): CoroutineContextProviding = get()
}
