package de.stefan.lang.shapebyte.shared.featuretoggles.data

import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import kotlinx.coroutines.flow.Flow

interface FeatureToggleDatasource {
    fun fetchFeatureToggle(identifier: String): Flow<LoadState<FeatureToggle>>
}
