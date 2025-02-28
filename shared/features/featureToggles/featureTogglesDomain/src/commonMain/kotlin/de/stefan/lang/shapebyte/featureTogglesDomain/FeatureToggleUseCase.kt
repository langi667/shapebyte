package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.foundationCore.loadstate.asResultFlow
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class FeatureToggleUseCase(
    val featureId: String,
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) {
    operator fun invoke() = loadFeatureToggleUseCase(featureId).asResultFlow()

    val isEnabled: Flow<Boolean> by lazy {
        invoke()
            .map {
                it.dataOrNull<FeatureToggle>()?.state?.isEnabled ?: false
            }
    }
}
