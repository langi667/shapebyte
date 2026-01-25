package de.stefan.lang.shapebyte.initializing

import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import org.koin.core.module.Module

/**
 * Will launch all app dependencies
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedInitializationUseCase() : BaseInitializationUseCase {
    override suspend fun initializeShared(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    )

    override fun initializeDependencyGraph(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    )
}
