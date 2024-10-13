package de.stefan.lang.shapebyte.featuretoggles.data

import de.stefan.lang.shapebyte.shared.data.LoadState
import kotlinx.coroutines.flow.Flow

interface FeatureToggleDatasource {
    fun fetchFeatureToggle(identifier: String): Flow<LoadState<FeatureToggle?>>
}
