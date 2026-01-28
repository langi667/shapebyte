package de.stefan.lang.coreutils

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.coreutils.CoreUtilsModule.contextProvider
import de.stefan.lang.coreutils.contract.nativecontext.ContextProvider

public object CoreUtilsModule :
    RootModule(
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
