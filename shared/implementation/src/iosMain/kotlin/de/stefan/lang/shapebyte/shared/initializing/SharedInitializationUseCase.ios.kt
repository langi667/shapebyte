package de.stefan.lang.shapebyte.shared.initializing

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.app.AppInfoProvider
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import kotlinx.coroutines.withContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/***
 * Will launch all app dependencies including
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class SharedInitializationUseCase actual constructor(
    coroutineContextProvider: () -> CoroutineContextProvider,
    coroutineScopeProvider: () -> CoroutineScopeProvider,
    deviceInfoProvider: () -> DeviceInfoProvider,
    appInfoProvider: () -> AppInfoProvider,
) : BaseInitializationUseCase(
    coroutineContextProvider,
    coroutineScopeProvider,
    deviceInfoProvider,
    appInfoProvider
) {
    actual override suspend fun initializeShared(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>
    ) {
        // If there is more data to initialize, work with combine
        // This needs top be called from main thread, because it performs UI operations on iOS
        // shifting it to another thread causes crashes
        withContext(coroutineContextProvider().mainDispatcher()  ) {
            deviceInfoProvider().readDeviceInfos().collect {
                stateFlow.value = AppInitializationState.INITIALIZED
            }
        }
    }

    actual override fun initializeDependencyGraph(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>
    ) {
        startKoin {
            modules(modules)
        }
    }
}