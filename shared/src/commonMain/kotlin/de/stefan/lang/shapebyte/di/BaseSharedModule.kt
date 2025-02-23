package de.stefan.lang.shapebyte.di

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.CoreModuleProviding
import de.stefan.lang.features.FeaturesModule
import de.stefan.lang.features.FeaturesModuleProviding
import de.stefan.lang.foundation.FoundationModule
import de.stefan.lang.foundation.FoundationModuleProviding
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.shapebyte.featureCore.FeatureCoreModule
import de.stefan.lang.shapebyte.featureCore.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.shapebyte.featureCore.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.shapebyte.features.home.HomeModule
import de.stefan.lang.shapebyte.features.home.HomeModuleProviding
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.WorkoutModuleProviding
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import org.koin.core.component.KoinComponent

interface AppInfoProviding {
    fun appInfo(): AppInfo
}

abstract class BaseSharedModule :
    KoinComponent,
    CoreModuleProviding by CoreModule,
    FoundationModuleProviding by FoundationModule,
    FeaturesModuleProviding by FeaturesModule,
    WorkoutModuleProviding by WorkoutModule,
    HomeModuleProviding by HomeModule,
    AppInfoProviding,
    SharedInitializationProviding {
    val modules =
        // TODO: improve this,maybe using annotation like @MainModule
        CoreModule.module +
            FoundationModule.module +
            WorkoutModule.module +
            HomeModule.module +
            FeaturesModule.module

    val testModules =
        CoreModule.testModule +
            FoundationModule.testModule +
            WorkoutModule.testModule +
            HomeModule.testModule +
            FeaturesModule.testModule

    private val sharedInitializationUseCase: SharedInitializationUseCase by lazy { SharedInitializationUseCase() }

    private lateinit var appInfo: AppInfo

    /**
     * Call this method before you access the modules or testModules !
     */

    fun setup(
        platformDependencies: PlatformDependencyProviding,
    ) {
        FeatureCoreModule.setup(platformDependencies)
        this.appInfo = platformDependencies.appInfo
    }

    override fun sharedInitializationUseCase(): SharedInitializationUseCase {
        return sharedInitializationUseCase
    }

    override fun appInfo(): AppInfo {
        return appInfo
    }

    abstract fun start(platformDependencies: PlatformDependencyProvider)
}
