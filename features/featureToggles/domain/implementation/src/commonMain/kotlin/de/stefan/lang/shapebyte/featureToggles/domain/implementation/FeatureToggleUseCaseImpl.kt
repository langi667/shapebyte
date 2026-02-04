package de.stefan.lang.shapebyte.featureToggles.domain.implementation

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.loadstate.asResultFlow
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureToggleUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class FeatureToggleUseCaseImpl(
    public val featureId: String,
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : FeatureToggleUseCase {
    public override operator fun invoke(): Flow<LoadState.Result<FeatureToggle>> =
        loadFeatureToggleUseCase(featureId).asResultFlow()

    public override val isEnabled: Flow<Boolean> by lazy {
        invoke()
            .map {
                it.dataOrNull<FeatureToggle>()?.state?.isEnabled ?: false
            }
    }
}
