package de.stefan.lang.shapebyte.featureToggles.data

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.coroutines.CoroutinesModule
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureTogglesDataModuleContract
import de.stefan.lang.shapebyte.featureToggles.data.implementation.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureToggles.data.implementation.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.features.featureToggles.data.generated.GeneratedDependencies
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.component.get

object FeatureTogglesDataModule :
    RootModule(
        globalBindings = {
            single<FeatureToggleRepository> {
                FeatureToggleRepository(
                    logger = LoggingModule.logger(),
                    dataSource = get(),
                )
            }
        },
        productionBindings = {
            single<FeatureToggleDatasource> {
                DefaultFeatureToggleDatasourceImpl(
                    logger = LoggingModule.logger(),
                    assetLoader = get(),
                    coroutineContextProviding = CoroutinesModule.coroutineContextProvider(),
                )
            }
        },
        testBindings = {
            single<FeatureToggleDatasource> { FeatureToggleDatasourceMock(logger = get()) }
        },
        dependencies = GeneratedDependencies.modules,
    ),
    FeatureTogglesDataModuleContract {
    override fun featureToggleRepository(): FeatureToggleRepository = get()
}
