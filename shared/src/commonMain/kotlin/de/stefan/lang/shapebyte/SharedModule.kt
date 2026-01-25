package de.stefan.lang.shapebyte

import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coreutils.CoreUtilsModuleProviding
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationPresentation.api.dimension.DimensionProvider
import de.stefan.lang.foundationUI.FoundationUIModule
import de.stefan.lang.shapebyte.di.SharedInitializationProviding
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModule
import de.stefan.lang.shapebyte.featureToggles.FeatureTogglesModuleProviding
import de.stefan.lang.shapebyte.features.home.HomeModule
import de.stefan.lang.shapebyte.features.home.HomeModuleProviding
import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.navigation.NavigationModuleProviding
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.WorkoutModuleProviding
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface AppInfoProviding {
    fun appInfo(): AppInfo
}

object SharedModule :
    KoinComponent,
    CoreUtilsModuleProviding by CoreUtilsModule,
    NavigationModuleProviding by NavigationModule,
    FeatureTogglesModuleProviding by FeatureTogglesModule,
    HomeModuleProviding by HomeModule,
    WorkoutModuleProviding by WorkoutModule,
    AppInfoProviding,
    SharedInitializationProviding {

    val modules =
        CoroutinesModule.module +
            CoreUtilsModule.module +
            FoundationCoreModule.module +
            FoundationUIModule.module +
            NavigationModule.module +
            FeatureTogglesModule.module +
            HomeModule.module +
            WorkoutModule.module

    val testModules =
        CoroutinesModule.testModules +
            CoreUtilsModule.testModules +
            FoundationCoreModule.testModules +
            FoundationUIModule.testModules +
            NavigationModule.testModules +
            FeatureTogglesModule.testModules +
            HomeModule.testModules +
            WorkoutModule.testModules

    private val sharedInitializationUseCase: SharedInitializationUseCase by lazy { SharedInitializationUseCase() }

    private lateinit var appInfo: AppInfo

    /**
     * Call this method before you access the modules or testModules !
     */

    fun setup(
        platformDependencies: PlatformDependencyProviding,
    ) {
        CoreUtilsModule.initialize(
            contextProvider = platformDependencies.appContextProvider,
        )

        FoundationCoreModule.initialize(
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
