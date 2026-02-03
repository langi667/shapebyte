package de.stefan.lang.shapebyte

import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.foundation.presentation.FoundationPresentationModule
import de.stefan.lang.foundation.presentation.contract.dimension.DimensionProvider
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.di.SharedInitializationProviding
import de.stefan.lang.shapebyte.featureTogglesDomain.FeatureTogglesDomainModule
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureTogglesDomainContract
import de.stefan.lang.shapebyte.features.home.presentation.HomePresentationModule
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomePresentationContract
import de.stefan.lang.shapebyte.features.navigation.NavigationModule
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationModuleContract
import de.stefan.lang.shapebyte.features.workout.WorkoutPresentationModule
import de.stefan.lang.shapebyte.features.workout.contract.WorkoutPresentationContract
import de.stefan.lang.shapebyte.features.workout.data.WorkoutDataModule
import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutDataContract
import de.stefan.lang.shapebyte.initializing.SharedInitializationUseCase
import de.stefan.lang.utils.logging.LoggingModule
import de.stefan.lang.utils.logging.contract.LoggingContract
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface AppInfoProviding {
    fun appInfo(): AppInfo
}

object SharedModule :
    KoinComponent,
    LoggingContract by LoggingModule,
    NavigationModuleContract by NavigationModule,
    FeatureTogglesDomainContract by FeatureTogglesDomainModule,
    HomePresentationContract by HomePresentationModule,
    WorkoutDataContract by WorkoutDataModule,
    WorkoutPresentationContract by WorkoutPresentationModule,
    AppInfoProviding,
    SharedInitializationProviding {

    val modules =
        CoroutinesModule.productionModules +
            LoggingModule.productionModules +
            CoreUtilsModule.productionModules +
            FoundationCoreModule.productionModules +
            FoundationPresentationModule.productionModules +
            NavigationModule.productionModules +
            FeatureTogglesDomainModule.productionModules +
            HomePresentationModule.productionModules +
            WorkoutDataModule.productionModules +
            WorkoutPresentationModule.productionModules

    val testModules =
        CoroutinesModule.testModules +
            CoreUtilsModule.testModules +
            FoundationCoreModule.testModules +
            FoundationPresentationModule.testModules +
            NavigationModule.testModules +
            FeatureTogglesDomainModule.testModules +
            HomePresentationModule.testModules +
            WorkoutDataModule.testModules +
            WorkoutPresentationModule.testModules

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

    fun deviceInfoProvider(): DeviceInfoProvider {
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
