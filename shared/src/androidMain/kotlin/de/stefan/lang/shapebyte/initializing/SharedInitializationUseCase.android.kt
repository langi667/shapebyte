package de.stefan.lang.shapebyte.initializing

import android.os.StrictMode
import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProvider
import de.stefan.lang.shapebyte.SharedModule

/**
 * Will setup all app dependencies, must be called in the beginning of the app launch
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedInitializationUseCase : BaseInitializationUseCase() {
    actual override suspend fun initializeShared(platformDependencies: PlatformDependencyProvider) {
        setupStrictMode()
        readDeviceInfos().collect {
            stateFlow.value = SharedInitializationState.INITIALIZED
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
