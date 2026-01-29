package de.stefan.lang.shapebyte.featureTogglesDomain.contract

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import kotlinx.coroutines.flow.Flow

public interface FeatureToggleUseCase {
    public operator fun invoke(): Flow<LoadState.Result<FeatureToggle>>
    public val isEnabled: Flow<Boolean>
}
