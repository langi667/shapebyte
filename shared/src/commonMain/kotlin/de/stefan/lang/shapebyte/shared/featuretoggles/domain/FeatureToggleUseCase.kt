package de.stefan.lang.shapebyte.shared.featuretoggles.domain

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class FeatureToggleUseCase(
    val featureId: String,
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) {
    operator fun invoke() = loadFeatureToggleUseCase(featureId)

    val isEnabled: Flow<Boolean> = invoke()
        .map { it.dataOrNull<FeatureToggle>()?.state?.isEnabled ?: false }
}
