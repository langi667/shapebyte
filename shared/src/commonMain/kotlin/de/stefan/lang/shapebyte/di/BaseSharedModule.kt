package de.stefan.lang.shapebyte.di

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.CoreModuleProviding
import de.stefan.lang.features.FeaturesModule
import de.stefan.lang.features.FeaturesModuleProviding
import de.stefan.lang.foundation.FoundationModule
import de.stefan.lang.foundation.FoundationModuleProviding
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.navigation.NavigationModule
import de.stefan.lang.navigation.NavigationModuleProviding
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import org.koin.core.component.KoinComponent

interface AppInfoProviding {
    fun appInfo(): AppInfo
}

abstract class BaseSharedModule :
    KoinComponent,
    CoreModuleProviding by CoreModule,
    FoundationModuleProviding by FoundationModule,
    NavigationModuleProviding by NavigationModule,
    FeaturesModuleProviding by FeaturesModule,
    AppInfoProviding,
    SharedInitializationProviding {
    val modules =
        // TODO: improve this,maybe using annotation like @MainModule
        CoreModule.module +
            FoundationModule.module +
            NavigationModule.module +
            FeaturesModule.module

    val testModules =
        CoreModule.testModule +
            FoundationModule.testModule +
            NavigationModule.testModule +
            FeaturesModule.testModule

    private val sharedInitializationUseCase: SharedInitializationUseCase by lazy { SharedInitializationUseCase() }

    private lateinit var appInfo: AppInfo

    /**
     * Call this method before you access the modules or testModules !
     */

    fun setup(
        platformDependencies: PlatformDependencyProviding,
    ) {
        CoreModule.initialize(
            contextProvider = platformDependencies.appContextProvider,
        )

        FoundationCoreModule.initialize(
            appResourceProvider = platformDependencies.appResourceProvider,
        )

        this.appInfo = platformDependencies.appInfo
    }

    override fun sharedInitializationUseCase(): SharedInitializationUseCase {
        return sharedInitializationUseCase
    }

    override fun appInfo(): AppInfo {
        return appInfo
    }

    abstract fun start(platformDependencies: PlatformDependencyProviding)
}
