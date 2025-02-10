package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.core.CoreModule
import de.stefan.lang.coreutils.coroutines.CoroutineContextProviding
import de.stefan.lang.coreutils.coroutines.CoroutineScopeProviding
import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider

interface UtilsModuleProviding {
    fun appInfo(): AppInfo
}

object UtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            // TODO: move to Common
        },
        appEnvironmentOnly = {
        },
        testEnvironmentOnly = {
        },
    ),
    UtilsModuleProviding {

    // TODO: move to DPI
    private lateinit var appInfo: AppInfo

    // Move to DPI
    fun initialize(
        coroutineContextProvider: CoroutineContextProviding,
        coroutineScopeProviding: CoroutineScopeProviding,
        appInfo: AppInfo,
        appContextProvider: ContextProvider,
        appResourceProvider: AppResourceProvider,
    ) {
        this.appInfo = appInfo // TODO: move to DPI

        CoreModule.initialize(
            contextProvider = appContextProvider,
            coroutineContextProvider = coroutineContextProvider,
            coroutineScopeProviding = coroutineScopeProviding,
        )

        FoundationCoreModule.initialize(
            appResourceProvider = appResourceProvider,
        )
    }

    override fun appInfo(): AppInfo = appInfo // TODO: move to DPI
}
