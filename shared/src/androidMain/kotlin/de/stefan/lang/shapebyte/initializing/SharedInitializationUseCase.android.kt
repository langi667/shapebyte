package de.stefan.lang.shapebyte.initializing

import android.os.StrictMode
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.shapebyte.SharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module

/**
 * Will setup all app dependencies, must be called in the beginning of the app launch
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedInitializationUseCase : BaseInitializationUseCase() {
    actual override suspend fun initializeShared(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    ) {
        setupStrictMode()
        readDeviceInfos().collect {
            stateFlow.value = AppInitializationState.INITIALIZED
        }
    }

    actual override fun initializeDependencyGraph(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    ) {
        val platformDependenciesAndroid = requireNotNull(platformDependencies as? PlatformDependencyProvider)

        GlobalContext.startKoin {
            androidContext(platformDependenciesAndroid.applicationContext)
            modules(modules)
        }
    }

    private fun setupStrictMode() {
        if (!SharedModule.appInfo().debugMode) {
            return
        }

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )
    }
}
