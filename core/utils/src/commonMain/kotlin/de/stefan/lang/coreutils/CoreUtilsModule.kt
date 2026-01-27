package de.stefan.lang.coreutils

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.coreutils.CoreUtilsModule.contextProvider
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.coroutines.contract.CoroutinesContract
import de.stefan.lang.utils.logging.LoggingModule
import de.stefan.lang.utils.logging.contract.LoggingContract

public object CoreUtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<ContextProvider> { contextProvider }
        },
    ),
    LoggingContract by LoggingModule,
    CoroutinesContract by CoroutinesModule {
    private lateinit var contextProvider: ContextProvider

    public fun initialize(
        contextProvider: ContextProvider,
    ) {
        this.contextProvider = contextProvider
    }
}
