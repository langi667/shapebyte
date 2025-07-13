package de.stefan.lang.featureToggles.api

import de.stefan.lang.foundationCore.api.loadstate.LoadState
import kotlinx.coroutines.flow.Flow

interface FeatureToggleUseCase {
    operator fun invoke(): Flow<LoadState.Result<FeatureToggle>>
    val isEnabled: Flow<Boolean>
}
