package de.stefan.lang.shapebyte.app.domain

import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/***
 * Will launch all app dependencies including
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AppInitializationUseCase : BaseAppInitializationUseCase() {
    actual override suspend fun initializeApplication(platformDependencies: PlatformDependencyProvider) {
        // If there is more data to initialize, work with combine
        // This needs top be called from main thread, because it performs UI operations on iOS
        // shifting it to another thread causes crashes
        withContext(Dispatchers.Main.immediate) {
            readDeviceInfos().collect {
                stateFlow.value = AppInitializationState.INITIALIZED
            }
        }
    }
}