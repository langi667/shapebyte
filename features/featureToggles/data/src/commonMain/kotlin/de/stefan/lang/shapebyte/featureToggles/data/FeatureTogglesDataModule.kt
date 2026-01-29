package de.stefan.lang.shapebyte.featureToggles.data

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureTogglesDataModuleContract
import de.stefan.lang.shapebyte.featureTogglesData.impl.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.component.get

object FeatureTogglesDataModule :
    RootModule(
        allEnvironments = {
            single<FeatureToggleRepository> {
                FeatureToggleRepository(
                    logger = get(),
                    dataSource = get()
                )
            }
        },
        appEnvironmentOnly = {
            single<FeatureToggleDatasource> {
                DefaultFeatureToggleDatasourceImpl(
                    logger = get(),
                    assetLoader = get(),
                    coroutineContextProviding = get(),
                )
            }
        },
        testEnvironmentOnly = {
            single<FeatureToggleDatasource> { FeatureToggleDatasourceMock(logger = get()) }
        },
        dependencies = listOf(LoggingModule),
    ),
    FeatureTogglesDataModuleContract {
    override fun featureToggleRepository(): FeatureToggleRepository = get()
}