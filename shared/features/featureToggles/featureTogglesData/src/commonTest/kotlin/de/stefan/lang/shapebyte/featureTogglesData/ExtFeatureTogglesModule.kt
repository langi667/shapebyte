package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import org.koin.core.component.get

val FeatureTogglesDataModule.featureToggleDatasource: FeatureToggleDatasource
    get() = this.get()

val FeatureTogglesDataModule.featureToggleDatasourceMock: FeatureToggleDatasourceMock
    get() = featureToggleDatasource as FeatureToggleDatasourceMock
