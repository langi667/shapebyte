package de.stefan.lang.featureToggles.api

import de.stefan.lang.foundationCore.api.loadstate.LoadState
import kotlinx.coroutines.flow.Flow

interface FeatureToggleLoading {
    operator fun invoke(identifier: String): Flow<LoadState<FeatureToggle>>
}
