package de.stefan.lang.shapebyte.shared.featuretoggles.data.impl

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FeatureToggleDatasourceImpl(
    override val logger: Logging,
) : FeatureToggleDatasource, Loggable {

    // TODO: add later once features get implemented
    private val featureToggles: HashMap<String, FeatureToggle> = hashMapOf()

    // TODO: implement
    // TODO: test
    override fun fetchFeatureToggle(identifier: String): Flow<LoadState<FeatureToggle?>> {
        return flowOf(LoadState.Success(featureToggles[identifier]))
    }
}
