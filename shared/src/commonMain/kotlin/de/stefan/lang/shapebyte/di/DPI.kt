package de.stefan.lang.shapebyte.di

import de.stefan.lang.core.CoreModule
import de.stefan.lang.core.CoreModuleProviding
import de.stefan.lang.foundation.core.FoundationCoreModule
import de.stefan.lang.foundation.core.FoundationCoreModuleProviding
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProviding
import de.stefan.lang.shapebyte.app.domain.AppInitializationUseCase
import de.stefan.lang.shapebyte.features.home.di.HomeModule
import de.stefan.lang.shapebyte.features.home.di.HomeModuleProviding
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModuleProviding
import de.stefan.lang.shapebyte.shared.featuretoggles.di.FeatureTogglesModule
import de.stefan.lang.shapebyte.shared.featuretoggles.di.FeatureTogglesModuleProviding
import de.stefan.lang.shapebyte.utils.di.UtilsModule
import de.stefan.lang.shapebyte.utils.di.UtilsModuleProviding
import org.koin.core.component.KoinComponent

abstract class BaseDPI :
    KoinComponent,
    UtilsModuleProviding by UtilsModule,
    WorkoutModuleProviding by WorkoutModule,
    HomeModuleProviding by HomeModule,
    FeatureTogglesModuleProviding by FeatureTogglesModule,
    CoreModuleProviding by CoreModule,
    FoundationCoreModuleProviding by FoundationCoreModule,
    AppInitializationProviding {
    val modules =
        CoreModule.module +
        FoundationCoreModule.module +
        UtilsModule.module +
        WorkoutModule.module +
        FeatureTogglesModule.module +
        HomeModule.module

    val testModules =
        CoreModule.testModule +
        FoundationCoreModule.testModule +
        UtilsModule.testModule +
        WorkoutModule.testModule +
        FeatureTogglesModule.testModule +
        HomeModule.testModule

    private val appInitializationUseCase: AppInitializationUseCase by lazy { AppInitializationUseCase() }

    /**
     * Call this method before you access the modules or testModules !
     */

    fun setup(
        data: PlatformDependencyProviding,
    ) {
        UtilsModule.initialize(
            coroutineContextProvider = data.coroutineContextProvider,
            coroutineScopeProviding = data.coroutineScopeProviding,
            appInfo = data.appInfo,
            appContextProvider = data.appContextProvider,
            appResourceProvider = data.appResourceProvider,
        )
    }

    override fun appInitializerUseCase(): AppInitializationUseCase {
        return appInitializationUseCase
    }

    abstract fun start(platformDependencies: PlatformDependencyProvider)
}

expect object DPI : BaseDPI {
    override fun start(platformDependencies: PlatformDependencyProvider)
}
