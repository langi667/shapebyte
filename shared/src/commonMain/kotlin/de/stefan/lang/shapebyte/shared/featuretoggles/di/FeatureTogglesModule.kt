package de.stefan.lang.shapebyte.shared.featuretoggles.di

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.FeatureToggleUseCase
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.utils.dicore.DIModuleDeclaration
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface FeatureTogglesModuleProviding {
    fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase
    fun featureToggleUseCase(featureId: String): FeatureToggleUseCase
}

object FeatureTogglesModule :
    DIModuleDeclaration(
        allEnvironments = {
            factory<FeatureToggleUseCase> { (featureId: String) ->
                FeatureToggleUseCase(
                    featureId = featureId,
                    loadFeatureToggleUseCase = get(),
                )
            }
            single<LoadFeatureToggleUseCase> { LoadFeatureToggleUseCase(logger = get(), repository = get()) }
            single<FeatureToggleRepository> { FeatureToggleRepository(logger = get(), datasource = get()) }
        },
        appEnvironmentOnly = {
            single<FeatureToggleDatasource> {
                FeatureToggleDatasourceImpl(
                    logger = get(),
                    assetLoader = get(),
                )
            }
        },
        testEnvironmentOnly = {
            single<FeatureToggleDatasource> { FeatureToggleDatasourceMock(logger = get()) }
        },
    ),
    FeatureTogglesModuleProviding {

    override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase = get()
    override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase = get(parameters = { parametersOf(featureId) })
}
