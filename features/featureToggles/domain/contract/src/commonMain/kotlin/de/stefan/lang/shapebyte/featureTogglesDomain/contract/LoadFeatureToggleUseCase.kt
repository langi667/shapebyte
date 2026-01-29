package de.stefan.lang.shapebyte.featureTogglesDomain.contract

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggle
import kotlinx.coroutines.flow.Flow

public interface LoadFeatureToggleUseCase {
    public operator fun invoke(identifier: String): Flow<LoadState<FeatureToggle>>
}
