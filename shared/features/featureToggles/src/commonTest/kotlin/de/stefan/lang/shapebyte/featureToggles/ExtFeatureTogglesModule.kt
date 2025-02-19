package de.stefan.lang.shapebyte.featureToggles

import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureToggles.data.impl.FeatureToggleDatasourceMock
import org.koin.core.component.get

val FeatureTogglesModule.featureToggleRepository: FeatureToggleRepository
    get() = this.get()

val FeatureTogglesModule.featureToggleDatasource: FeatureToggleDatasource
    get() = this.get()

val FeatureTogglesModule.featureToggleDatasourceMock: FeatureToggleDatasourceMock
    get() = featureToggleDatasource as FeatureToggleDatasourceMock
