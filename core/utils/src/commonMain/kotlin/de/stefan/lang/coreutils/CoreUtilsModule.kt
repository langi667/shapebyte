package de.stefan.lang.coreutils

import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.coreutils.CoreUtilsModule.contextProvider
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider

public object CoreUtilsModule :
    RootDIModule(
        allEnvironments = {
            single<ContextProvider> { contextProvider }
        },
    ) {
    private lateinit var contextProvider: ContextProvider

    public fun initialize(
        contextProvider: ContextProvider,
    ) {
        this.contextProvider = contextProvider
    }
}
