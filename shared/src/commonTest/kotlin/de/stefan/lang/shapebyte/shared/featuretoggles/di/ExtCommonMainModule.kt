package de.stefan.lang.shapebyte.shared.featuretoggles.di

import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.utils.assets.mocks.FileAssetLoaderMock
import org.koin.core.component.get

val DPI.featureToggleDatasource: FeatureToggleDatasource
    get() = this.get()

// check if needed since we can use the mock directly
val DPI.featureToggleDatasourceMock: FeatureToggleDatasourceMock
    get() = featureToggleDatasource as FeatureToggleDatasourceMock

val DPI.assetLoaderMock: FileAssetLoaderMock
    get() = fileAssetLoader() as FileAssetLoaderMock
