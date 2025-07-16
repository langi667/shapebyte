package de.stefan.lang.shapebyte.initializing

import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/***
 * Will launch all app dependencies including
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedInitializationUseCase : BaseInitializationUseCase() {

    actual override suspend fun initializeShared(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>
    ) {
        // If there is more data to initialize, work with combine
        // This needs top be called from main thread, because it performs UI operations on iOS
        // shifting it to another thread causes crashes
        withContext(Dispatchers.Main.immediate) {
            readDeviceInfos().collect {
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