package de.stefan.lang.featureToggles.api

import de.stefan.lang.foundationCore.api.loadstate.asResultFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeatureToggleUseCase(
    val featureId: String,
    private val featureTogglesLoader: FeatureToggleLoading,
) {
    operator fun invoke() = featureTogglesLoader(featureId).asResultFlow()

    val isEnabled: Flow<Boolean> by lazy {
        invoke()
            .map {
                it.dataOrNull<FeatureToggle>()?.state?.isEnabled ?: false
            }
    }
}
