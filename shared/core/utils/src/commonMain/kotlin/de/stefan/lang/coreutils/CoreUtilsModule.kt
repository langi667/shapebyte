package de.stefan.lang.coreutils

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.coreutils.CoreUtilsModule.contextProvider
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.coreutils.logging.impl.Logger
import de.stefan.lang.coreutils.logging.mocks.SilentLogger
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.coroutines.CoroutinesModuleProviding
import org.koin.core.component.get

interface CoreUtilsModuleProviding {
    fun logger(): Logging
}

object CoreUtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<ContextProvider> { contextProvider }
        },
        appEnvironmentOnly = {
            single<Logging> { Logger() }
        },
        testEnvironmentOnly = {
            single<Logging> { SilentLogger() }
        },
    ),
    CoreUtilsModuleProviding,
    CoroutinesModuleProviding by CoroutinesModule {
    private lateinit var contextProvider: ContextProvider

    fun initialize(
        contextProvider: ContextProvider,
    ) {
        this.contextProvider = contextProvider
    }

    override fun logger(): Logging = get()
}
