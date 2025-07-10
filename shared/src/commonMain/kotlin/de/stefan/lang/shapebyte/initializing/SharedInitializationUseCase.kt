package de.stefan.lang.shapebyte.initializing

import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProvider

/**
 * Will launch all app dependencies including
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedInitializationUseCase() : BaseInitializationUseCase {
    override suspend fun initializeShared(platformDependencies: PlatformDependencyProvider)
}
