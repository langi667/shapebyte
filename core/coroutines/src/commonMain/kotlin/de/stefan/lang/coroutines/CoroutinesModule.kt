package de.stefan.lang.coroutines

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.coroutines.contract.CoreCoroutinesContract
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.coroutines.implementation.CoroutineContextProvider
import de.stefan.lang.coroutines.implementation.CoroutineScopeProvider
import de.stefan.lang.coroutines.implementation.test.TestCoroutineContextProvider
import de.stefan.lang.coroutines.implementation.test.TestCoroutineScopeProvider
import de.stefan.lang.shapebyte.core.coroutines.generated.GeneratedDependencies
import org.koin.core.component.get

public object CoroutinesModule :
    RootModule(
        productionBindings = {
            single<CoroutineScopeProviding> { CoroutineScopeProvider() }
            single<CoroutineContextProviding> { CoroutineContextProvider() }
        },
        testBindings = {
            single<CoroutineScopeProviding> { TestCoroutineScopeProvider }
            single<CoroutineContextProviding> { TestCoroutineContextProvider }
        },
        dependencies = GeneratedDependencies.modules,
    ),
    CoreCoroutinesContract {
    public override fun coroutineScopeProvider(): CoroutineScopeProviding = get()
    public override fun coroutineContextProvider(): CoroutineContextProviding = get()
}
