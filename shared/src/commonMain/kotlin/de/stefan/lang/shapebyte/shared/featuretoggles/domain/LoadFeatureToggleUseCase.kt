package de.stefan.lang.shapebyte.shared.featuretoggles.domain

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
// TODO: remove, merge with FeatureToggleUseCase
class LoadFeatureToggleUseCase(
    private val repository: FeatureToggleRepository,
    override val logger: Logging,
) : Loggable {
    operator fun invoke(identifier: String) = repository.fetchFeatureToggle(identifier)
}
