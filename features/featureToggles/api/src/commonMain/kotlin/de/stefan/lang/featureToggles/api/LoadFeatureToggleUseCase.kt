package de.stefan.lang.featureToggles.api

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import kotlinx.coroutines.flow.Flow

interface LoadFeatureToggleUseCase {
    operator fun invoke(identifier: String): Flow<LoadState<FeatureToggle>>
}
