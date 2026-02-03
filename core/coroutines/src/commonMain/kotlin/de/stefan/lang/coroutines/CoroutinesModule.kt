package de.stefan.lang.coroutines

import de.stefan.lang.coroutines.contract.CoreCoroutinesContract
import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.coroutines.implementation.CoroutineContextProviderImpl
import de.stefan.lang.coroutines.implementation.CoroutineScopeProviderImpl
import de.stefan.lang.coroutines.implementation.test.TestCoroutineContextProvider
import de.stefan.lang.coroutines.implementation.test.TestCoroutineScopeProvider
import de.stefan.lang.shapebyte.core.coroutines.generated.Dependencies
import de.stefan.lang.shapebyte.core.coroutines.generated.Module
import org.koin.core.component.get

public object CoroutinesModule :
    Module(
        productionBindings = {
            single<CoroutineScopeProvider> { CoroutineScopeProviderImpl() }
            single<CoroutineContextProvider> { CoroutineContextProviderImpl() }
        },
        testBindings = {
            single<CoroutineScopeProvider> { TestCoroutineScopeProvider }
            single<CoroutineContextProvider> { TestCoroutineContextProvider }
        },
    ),
    CoreCoroutinesContract {
    public override fun coroutineScopeProvider(): CoroutineScopeProvider = get()
    public override fun coroutineContextProvider(): CoroutineContextProvider = get()
}
