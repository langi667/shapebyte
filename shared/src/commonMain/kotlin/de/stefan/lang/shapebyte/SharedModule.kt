package de.stefan.lang.shapebyte

import de.stefan.lang.coreutils.CoreUtilsModule
import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.app.AppInfoProvider
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.foundation.presentation.contract.FoundationPresentationContract
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.shapebyte.features.home.presentation.contract.HomePresentationContract
import de.stefan.lang.shapebyte.features.navigation.contract.NavigationModuleContract
import de.stefan.lang.shapebyte.features.workout.contract.WorkoutPresentationContract
import de.stefan.lang.shapebyte.features.workout.data.contract.WorkoutDataContract
import de.stefan.lang.shapebyte.shared.SharedModuleContract
import de.stefan.lang.shapebyte.shared.generated.Dependencies
import de.stefan.lang.shapebyte.shared.generated.Module
import de.stefan.lang.shapebyte.shared.initializing.InitializationUseCase
import de.stefan.lang.shapebyte.shared.initializing.SharedInitializationUseCase
import de.stefan.lang.utils.logging.contract.LoggingContract
import org.koin.core.component.get
import org.koin.core.module.Module as KoinModule

public object SharedModule :
    Module(),
    SharedModuleContract,
    AppInfoProvider,
    LoggingContract by Dependencies,
    HomePresentationContract by Dependencies,
    WorkoutDataContract by Dependencies,
    WorkoutPresentationContract by Dependencies,
    NavigationModuleContract by Dependencies,
    FoundationPresentationContract by Dependencies {

    internal val productionModules: List<KoinModule>
        get() = Dependencies.modules.map { it.productionDiModule }

    internal val testModules: List<KoinModule>
        get() = Dependencies.modules.map { it.testDiModule }

    private val sharedInitializationUseCase: InitializationUseCase by lazy {
        SharedInitializationUseCase(
            coroutineContextProvider = { Dependencies.coroutineContextProvider() },
            coroutineScopeProvider = { Dependencies.coroutineScopeProvider() },
            deviceInfoProvider = { Dependencies.deviceInfoProvider() },
            appInfoProvider = { this },
        )
    }

    private lateinit var appInfo: AppInfo

    /**
     * Call this method before you access the modules or testModules !
     */

    public fun setup(
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

    public fun deviceInfoProvider(): DeviceInfoProvider {
        return get()
    }

    override fun sharedInitializationUseCase(): InitializationUseCase {
        return sharedInitializationUseCase
    }

    override fun appInfo(): AppInfo {
        return appInfo
    }

    public fun start(platformDependencies: PlatformDependencyProviding) {
        setup(platformDependencies)

        sharedInitializationUseCase().invoke(
            platformDependencies,
            modules = productionModules,
        )
    }
}
