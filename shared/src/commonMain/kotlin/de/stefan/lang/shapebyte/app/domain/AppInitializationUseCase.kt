package de.stefan.lang.shapebyte.app.domain

import de.stefan.lang.shapebyte.app.data.PlatformDependencyProvider

/**
 * Will launch all app dependencies including
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AppInitializationUseCase() : BaseAppInitializationUseCase {
    override suspend fun initializeApplication(platformDependencies: PlatformDependencyProvider)
}
