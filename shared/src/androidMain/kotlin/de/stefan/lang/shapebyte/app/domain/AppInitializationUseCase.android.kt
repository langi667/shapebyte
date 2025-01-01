package de.stefan.lang.shapebyte.app.domain

import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider

/**
 * Will setup all app dependencies, must be called in the beginning of the app launch
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AppInitializationUseCase : BaseAppInitializationUseCase() {
    actual override suspend fun initializeApplication(platformDependencies: PlatformDependencyProvider) {
        // If there is more data to initialize, work with combine
        readDeviceInfos().collect {
            stateFlow.value = AppInitializationState.INITIALIZED
        }
    }
}
