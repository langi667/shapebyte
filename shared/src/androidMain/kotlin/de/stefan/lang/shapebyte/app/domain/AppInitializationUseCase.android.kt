package de.stefan.lang.shapebyte.app.domain

import android.os.StrictMode
import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider
import de.stefan.lang.shapebyte.di.DPI

/**
 * Will setup all app dependencies, must be called in the beginning of the app launch
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AppInitializationUseCase : BaseAppInitializationUseCase() {
    actual override suspend fun initializeApplication(platformDependencies: PlatformDependencyProvider) {
        setupStrictMode()
        readDeviceInfos().collect {
            stateFlow.value = AppInitializationState.INITIALIZED
        }
    }

    private fun setupStrictMode() {
        if (!DPI.appInfo().debugMode) {
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
