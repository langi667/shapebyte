package de.stefan.lang.core

import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coreutils.CoreUtilsModuleProviding
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.coroutines.CoroutinesModuleProviding

interface CoreModuleProviding : CoreUtilsModuleProviding, CoroutinesModuleProviding

object CoreModule :
    RootDIModule(
        listOf(CoreUtilsModule, CoroutinesModule),
    ),
    CoreModuleProviding,
    CoreUtilsModuleProviding by CoreUtilsModule,
    CoroutinesModuleProviding by CoroutinesModule {

    fun initialize(
        contextProvider: ContextProvider,
    ) {
        CoreUtilsModule.initialize(
            contextProvider,
        )
    }
}
