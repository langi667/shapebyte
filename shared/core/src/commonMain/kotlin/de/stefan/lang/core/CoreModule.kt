package de.stefan.lang.core

import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coreutils.CoreUtilsModuleProviding
import de.stefan.lang.coreutils.coroutines.CoroutineContextProviding
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import de.stefan.lang.coreutils.di.RootDIModule
import de.stefan.lang.coreutils.nativecontext.ContextProvider

interface CoreModuleProviding : CoreUtilsModuleProviding

object CoreModule :
    RootDIModule(
        listOf(CoreUtilsModule),
    ),
    CoreModuleProviding,
    CoreUtilsModuleProviding by CoreUtilsModule {

    fun initialize(
        coroutineContextProvider: CoroutineContextProviding,
        coroutineScopeProviding: CoroutineScopeProviding,
        contextProvider: ContextProvider,
    ) {
        CoreUtilsModule.initialize(
            coroutineContextProvider,
            coroutineScopeProviding,
            contextProvider,
        )
    }
}
