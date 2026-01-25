package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.foundation.core.contract.loadstate.LoadState

interface FeatureToggleDatasource {
    suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle>
}
