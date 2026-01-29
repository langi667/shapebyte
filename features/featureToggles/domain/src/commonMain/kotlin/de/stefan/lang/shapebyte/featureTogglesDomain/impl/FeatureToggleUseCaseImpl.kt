package de.stefan.lang.shapebyte.featureTogglesDomain.impl

import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggle
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureToggleUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.foundation.core.contract.loadstate.asResultFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeatureToggleUseCaseImpl(
    val featureId: String,
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : FeatureToggleUseCase {
    override operator fun invoke() = loadFeatureToggleUseCase(featureId).asResultFlow()

    override val isEnabled: Flow<Boolean> by lazy {
        invoke()
            .map {
                it.dataOrNull<FeatureToggle>()?.state?.isEnabled ?: false
            }
    }
}
