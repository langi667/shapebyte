package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.CoreModuleProviding
import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.coroutines.CoroutineScopeProviding
import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.core.resources.impl.AppResourceProvider

import de.stefan.lang.core.app.AppInfo


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
    CoreModuleProviding by CoreModule,
    UtilsModuleProviding {

    // TODO: move to DPI
    private lateinit var appInfo: AppInfo

    // Move to DPI
    fun initialize(
        coroutineContextProvider: CoroutineContextProviding,
        coroutineScopeProviding: CoroutineScopeProviding,
        appInfo: AppInfo,
        appContextProvider: AppContextProvider,
        appResourceProvider: AppResourceProvider,
    ) {
        this.appInfo = appInfo // TODO: move to DPI

        CoreModule.initialize(
            appContextProvider = appContextProvider,
            appResourceProvider = appResourceProvider,
            coroutineContextProvider = coroutineContextProvider,
            coroutineScopeProviding = coroutineScopeProviding,
        )
    }

    override fun appInfo(): AppInfo = appInfo // TODO: move to DPI
}
