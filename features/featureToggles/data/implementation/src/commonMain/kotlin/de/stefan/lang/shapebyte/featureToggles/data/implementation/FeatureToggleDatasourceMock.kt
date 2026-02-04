package de.stefan.lang.shapebyte.featureToggles.data.implementation

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleError
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger

public class FeatureToggleDatasourceMock(
    public override val logger: Logger,
    private val featureToggles: MutableMap<String, FeatureToggle> = hashMapOf(),
) : FeatureToggleDatasource, Loggable {

    public override suspend fun fetchFeatureToggle(identifier: String): LoadState.Result<FeatureToggle> {
        val state = featureToggles[identifier]?.let {
            LoadState.Success(it)
        } ?: run {
            LoadState.Error(FeatureToggleError.NotFound(identifier))
        }

        return state
    }

    public fun addFeatureToggle(featureToggle: FeatureToggle) {
        featureToggles[featureToggle.identifier] = featureToggle
    }

    public fun setFeatureToggles(newToggles: List<FeatureToggle>) {
        featureToggles.clear()
        featureToggles.putAll(newToggles.associateBy { it.identifier })
    }
}
