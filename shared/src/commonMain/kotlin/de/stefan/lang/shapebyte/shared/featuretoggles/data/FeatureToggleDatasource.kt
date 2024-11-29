package de.stefan.lang.shapebyte.shared.featuretoggles.data

import de.stefan.lang.shapebyte.shared.loading.data.LoadState

interface FeatureToggleDatasource {
    suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle>
}
