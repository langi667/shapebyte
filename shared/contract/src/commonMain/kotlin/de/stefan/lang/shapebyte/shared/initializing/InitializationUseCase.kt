package de.stefan.lang.shapebyte.shared.initializing

import de.stefan.lang.foundation.core.contract.platformdependencies.PlatformDependencyProviding
import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

public interface InitializationUseCase {
    public val flow: Flow<AppInitializationState>
    public val state: AppInitializationState
    public val isInitialized: Boolean

    public fun invoke(
        platformDependencies: PlatformDependencyProviding,
        modules: List<Module>,
    ): Flow<AppInitializationState>
}
