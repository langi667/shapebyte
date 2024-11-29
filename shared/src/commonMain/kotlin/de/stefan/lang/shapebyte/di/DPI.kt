package de.stefan.lang.shapebyte.di

import de.stefan.lang.shapebyte.features.home.di.HomeModule
import de.stefan.lang.shapebyte.features.home.di.HomeModuleProviding
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.di.WorkoutModuleProviding
import de.stefan.lang.shapebyte.shared.featuretoggles.di.FeatureTogglesModule
import de.stefan.lang.shapebyte.shared.featuretoggles.di.FeatureTogglesModuleProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProvider
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProvider
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding
import de.stefan.lang.shapebyte.utils.di.UtilsModule
import de.stefan.lang.shapebyte.utils.di.UtilsModuleProviding
import org.koin.core.component.KoinComponent

object DPI :
    KoinComponent,
    UtilsModuleProviding by UtilsModule,
    WorkoutModuleProviding by WorkoutModule,
    HomeModuleProviding by HomeModule,
    FeatureTogglesModuleProviding by FeatureTogglesModule {

    val modules = UtilsModule.module +
        WorkoutModule.module +
        FeatureTogglesModule.module +
        HomeModule.module

    val testModules = UtilsModule.testModule +
        WorkoutModule.testModule +
        FeatureTogglesModule.testModule +
        HomeModule.testModule

    /**
     * Call this method before you access the modules or testModules !
     */
    fun setup(
        coroutineContextProvider: CoroutineContextProviding = CoroutineContextProvider(),
        coroutineScopeProviding: CoroutineScopeProviding = CoroutineScopeProvider(),
    ) {
        UtilsModule.initialize(
            coroutineContextProvider = coroutineContextProvider,
            coroutineScopeProviding = coroutineScopeProviding,
        )
    }
}
