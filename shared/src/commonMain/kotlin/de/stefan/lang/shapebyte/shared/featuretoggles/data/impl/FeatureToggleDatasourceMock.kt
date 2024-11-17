package de.stefan.lang.shapebyte.shared.featuretoggles.data.impl

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FeatureToggleDatasourceMock(
    override val logger: Logging,
    private val featureToggles: MutableMap<String, FeatureToggle> = hashMapOf(),
) : FeatureToggleDatasource, Loggable {

    override fun fetchFeatureToggle(identifier: String): Flow<LoadState<FeatureToggle>> {
        val state = featureToggles[identifier]?.let {
            LoadState.Success(it)
        } ?: run {
            LoadState.Error(FeatureToggleError.NotFound(identifier))
        }

        return flowOf(state)
    }

    fun addFeatureToggle(featureToggle: FeatureToggle) {
        featureToggles[featureToggle.identifier] = featureToggle
    }

    fun setFeatureToggles(newToggles: List<FeatureToggle>) {
        featureToggles.clear()
        featureToggles.putAll(newToggles.associateBy { it.identifier })
    }
}
