package de.stefan.lang.shapebyte.featureToggles.data

import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureTogglesDataModuleContract
import de.stefan.lang.shapebyte.featureToggles.data.implementation.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureToggles.data.implementation.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.features.featureToggles.data.generated.Dependencies
import de.stefan.lang.shapebyte.features.featureToggles.data.generated.Module
import org.koin.core.component.get

object FeatureTogglesDataModule :
    Module(
        globalBindings = {
            single<FeatureToggleRepository> {
                FeatureToggleRepository(
                    logger = Dependencies.logger(),
                    dataSource = get(),
                )
            }
        },
        productionBindings = {
            single<FeatureToggleDatasource> {
                DefaultFeatureToggleDatasourceImpl(
                    logger = Dependencies.logger(),
                    assetLoader = Dependencies.fileAssetLoader(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                )
            }
        },
        testBindings = {
            single<FeatureToggleDatasource> {
                FeatureToggleDatasourceMock(
                    logger = Dependencies.logger(),
                )
            }
        },
    ),
    FeatureTogglesDataModuleContract {
    override fun featureToggleRepository(): FeatureToggleRepository = get()
}
