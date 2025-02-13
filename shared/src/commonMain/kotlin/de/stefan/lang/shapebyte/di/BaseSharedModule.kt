package de.stefan.lang.shapebyte.di

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.CoreModuleProviding
import de.stefan.lang.foundation.FoundationModule
import de.stefan.lang.foundation.FoundationModuleProviding
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationCore.app.AppInfo
import de.stefan.lang.shapebyte.features.home.di.HomeModule
import de.stefan.lang.shapebyte.features.home.di.HomeModuleProviding
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModuleProviding
import de.stefan.lang.shapebyte.initializing.PlatformDependencyProvider
import de.stefan.lang.shapebyte.initializing.PlatformDependencyProviding
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import org.koin.core.component.KoinComponent

interface AppInfoProviding {
    fun appInfo(): AppInfo
}

abstract class BaseSharedModule :
    KoinComponent,
    CoreModuleProviding by CoreModule,
    FoundationModuleProviding by FoundationModule,
    WorkoutModuleProviding by WorkoutModule,
    HomeModuleProviding by HomeModule,
    AppInfoProviding,
    SharedInitializationProviding {
    val modules =
        CoreModule.module +
            FoundationModule.module +
            WorkoutModule.module +
            HomeModule.module

    val testModules =
        CoreModule.testModule +
            FoundationModule.testModule +
            WorkoutModule.testModule +
            HomeModule.testModule

    private val sharedInitializationUseCase: SharedInitializationUseCase by lazy { SharedInitializationUseCase() }

    private lateinit var appInfo: AppInfo

    /**
     * Call this method before you access the modules or testModules !
     */

    fun setup(
        data: PlatformDependencyProviding,
    ) {
        CoreModule.initialize(
            contextProvider = data.appContextProvider,
            coroutineContextProvider = data.coroutineContextProvider,
            coroutineScopeProviding = data.coroutineScopeProviding,
        )

        FoundationCoreModule.initialize(
            appResourceProvider = data.appResourceProvider,
        )

        this.appInfo = data.appInfo
    }

    override fun sharedInitializationUseCase(): SharedInitializationUseCase {
        return sharedInitializationUseCase
    }

    override fun appInfo(): AppInfo {
        return appInfo
    }

    abstract fun start(platformDependencies: PlatformDependencyProvider)
}
