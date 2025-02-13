package de.stefan.lang.shapebyte.initializing

/**
 * Will launch all app dependencies including
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedInitializationUseCase() : BaseInitializationUseCase {
    override suspend fun initializeShared(platformDependencies: PlatformDependencyProvider)
}
