package de.stefan.lang.shapebyte

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.CoreModuleProviding
import de.stefan.lang.features.FeaturesModule
import de.stefan.lang.features.FeaturesModuleProviding
import de.stefan.lang.foundation.FoundationModule
import de.stefan.lang.foundationCore.api.app.AppInfo
import de.stefan.lang.foundationCore.api.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.foundationPresentation.api.dimension.DimensionProvider
import de.stefan.lang.shapebyte.di.SharedInitializationProviding
import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.navigation.NavigationModuleProviding
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface AppInfoProviding {
    fun appInfo(): AppInfo
}

object SharedModule :
    KoinComponent,
    CoreModuleProviding by CoreModule,
    NavigationModuleProviding by NavigationModule,
    FeaturesModuleProviding by FeaturesModule,
    AppInfoProviding,
    SharedInitializationProviding {

    val modules =
        CoreModule.module +
        FoundationModule.module +
        NavigationModule.module +
        FeaturesModule.module

    val testModules =
        CoreModule.testModules +
            FoundationModule.testModules +
            NavigationModule.testModules +
            FeaturesModule.testModules

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

        FoundationModule.initialize(
            appResourceProvider = platformDependencies.appResourceProvider,
        )

        this.appInfo = platformDependencies.appInfo
    }

    fun deviceInfoProvider(): DeviceInfoProviding {
        return get()
    }

    fun dimensionProvider(): DimensionProvider {
        return get()
    }

    override fun sharedInitializationUseCase(): SharedInitializationUseCase {
        return sharedInitializationUseCase
    }

    override fun appInfo(): AppInfo {
        return appInfo
    }

    fun start(platformDependencies: PlatformDependencyProviding) {
        setup(platformDependencies)

        sharedInitializationUseCase().invoke(platformDependencies, modules = modules)
    }
}
