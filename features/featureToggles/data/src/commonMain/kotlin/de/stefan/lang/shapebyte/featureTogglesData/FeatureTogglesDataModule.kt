package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.featureTogglesData.impl.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.component.get

interface FeatureTogglesDataModuleProviding {
    fun featureToggleRepository(): FeatureToggleRepository
}

object FeatureTogglesDataModule :
    RootModule(
        allEnvironments = {
            single<FeatureToggleRepository> { FeatureToggleRepository(logger = get(), dataSource = get()) }
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
    FeatureTogglesDataModuleProviding {
    override fun featureToggleRepository(): FeatureToggleRepository = get()
}
