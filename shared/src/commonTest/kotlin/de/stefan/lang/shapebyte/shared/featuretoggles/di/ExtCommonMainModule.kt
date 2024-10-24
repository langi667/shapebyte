package de.stefan.lang.shapebyte.shared.featuretoggles.di

import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import org.koin.core.component.get

val DPI.featureToggleDatasource: FeatureToggleDatasource
    get() = this.get()

val DPI.featureToggleDatasourceMock: FeatureToggleDatasourceMock
    get() = featureToggleDatasource as FeatureToggleDatasourceMock
