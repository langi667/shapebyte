package de.stefan.lang.coroutines

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.coreCoroutinesProvidingTest.TestCoroutineContextProvider
import de.stefan.lang.coreCoroutinesProvidingTest.TestCoroutineScopeProvider
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.coroutines.api.CoroutineScopeProviding
import org.koin.core.component.get

interface CoroutinesModuleProviding {
    fun coroutineScopeProvider(): CoroutineScopeProviding
    fun coroutineContextProvider(): CoroutineContextProviding
}

object CoroutinesModule :
    DIModuleDeclaration(
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
    CoroutinesModuleProviding {
    override fun coroutineScopeProvider(): CoroutineScopeProviding = get()
    override fun coroutineContextProvider(): CoroutineContextProviding = get()
}
