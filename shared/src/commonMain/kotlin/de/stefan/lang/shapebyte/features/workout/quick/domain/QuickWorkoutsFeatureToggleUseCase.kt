package de.stefan.lang.shapebyte.features.workout.quick.domain

import de.stefan.lang.shapebyte.features.core.domain.FeatureId
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.FeatureToggleUseCase
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.LoadFeatureToggleUseCase

class QuickWorkoutsFeatureToggleUseCase(
    loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
) : FeatureToggleUseCase(FeatureId.QUICK_WORKOUTS.name, loadFeatureToggleUseCase)
