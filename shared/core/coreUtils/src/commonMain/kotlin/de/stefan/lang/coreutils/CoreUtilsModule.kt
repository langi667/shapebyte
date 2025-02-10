package de.stefan.lang.coreutils

import de.stefan.lang.coreutils.CoreUtilsModule.contextProvider
import de.stefan.lang.coreutils.CoreUtilsModule.coroutineContextProvider
import de.stefan.lang.coreutils.CoreUtilsModule.coroutineScopeProvider
import de.stefan.lang.coreutils.coroutines.CoroutineContextProviding
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.coreutils.logging.impl.Logger
import de.stefan.lang.coreutils.logging.mocks.SilentLogger
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import org.koin.core.component.get

interface CoreUtilsModuleProviding {
    fun logger(): Logging
    fun coroutineContextProvider(): CoroutineContextProviding
    fun coroutineScopeProvider(): CoroutineScopeProviding
}

object CoreUtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<ContextProvider> { contextProvider }
            single<CoroutineContextProviding> { coroutineContextProvider }
            single<CoroutineScopeProviding> { coroutineScopeProvider }
        },
        appEnvironmentOnly = {
            single<Logging> { Logger() }
        },
        testEnvironmentOnly = {
            single<Logging> { SilentLogger() }
        },
    ),
    CoreUtilsModuleProviding {
    private lateinit var coroutineContextProvider: CoroutineContextProviding
    private lateinit var coroutineScopeProvider: CoroutineScopeProviding
    private lateinit var contextProvider: ContextProvider

    fun initialize(
        coroutineContextProvider: CoroutineContextProviding,
        coroutineScopeProvider: CoroutineScopeProviding,
        contextProvider: ContextProvider,
    ) {
        this.coroutineContextProvider = coroutineContextProvider
        this.coroutineScopeProvider = coroutineScopeProvider
        this.contextProvider = contextProvider
    }

    override fun logger(): Logging = get()
    override fun coroutineContextProvider(): CoroutineContextProviding = coroutineContextProvider
    override fun coroutineScopeProvider(): CoroutineScopeProviding = coroutineScopeProvider
}
