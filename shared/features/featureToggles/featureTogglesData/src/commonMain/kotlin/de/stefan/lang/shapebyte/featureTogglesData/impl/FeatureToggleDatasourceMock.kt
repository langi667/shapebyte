package de.stefan.lang.shapebyte.featureTogglesData.impl

import de.stefan.lang.coreutils.api.logging.Loggable
import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggle
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleDatasource

class FeatureToggleDatasourceMock(
    override val logger: Logging,
    private val featureToggles: MutableMap<String, FeatureToggle> = hashMapOf(),
) : FeatureToggleDatasource, Loggable {

    override suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle> {
        val state = featureToggles[identifier]?.let {
            LoadState.Success(it)
        } ?: run {
            LoadState.Error(FeatureToggleError.NotFound(identifier))
        }

        return state
    }

    fun addFeatureToggle(featureToggle: FeatureToggle) {
        featureToggles[featureToggle.identifier] = featureToggle
    }

    fun setFeatureToggles(newToggles: List<FeatureToggle>) {
        featureToggles.clear()
        featureToggles.putAll(newToggles.associateBy { it.identifier })
    }
}
