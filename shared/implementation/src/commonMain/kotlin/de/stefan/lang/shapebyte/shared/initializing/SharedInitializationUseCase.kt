package de.stefan.lang.shapebyte.shared.initializing

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.app.AppInfoProvider
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import org.koin.core.module.Module

/**
 * Will launch all app dependencies
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class SharedInitializationUseCase(
    // passing a lazy interface to avoid DI issues
    coroutineContextProvider: () -> CoroutineContextProvider,
    // passing a lazy interface to avoid DI issues
    coroutineScopeProvider: () -> CoroutineScopeProvider,
    // passing a lazy interface to avoid DI issues
    deviceInfoProvider: () -> DeviceInfoProvider,
    // passing a lazy interface to avoid DI issues
    appInfoProvider: () -> AppInfoProvider,
) : BaseInitializationUseCase {
    override suspend fun initializeShared(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    )

    override fun initializeDependencyGraph(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    )
}
