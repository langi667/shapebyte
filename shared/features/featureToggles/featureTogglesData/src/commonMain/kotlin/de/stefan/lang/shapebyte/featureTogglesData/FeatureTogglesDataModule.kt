package de.stefan.lang.shapebyte.featureTogglesData

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.shapebyte.featureTogglesData.impl.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import org.koin.core.component.get

interface FeatureTogglesDataModuleProviding {
    fun featureToggleRepository(): FeatureToggleRepository
}

object FeatureTogglesDataModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<FeatureToggleRepository> { FeatureToggleRepository(logger = get(), defaultFeatureTogglesDatasource = get()) }
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
    ),
    FeatureTogglesDataModuleProviding {
    override fun featureToggleRepository(): FeatureToggleRepository = get()
}
