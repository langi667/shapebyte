package de.stefan.lang.shapebyte.shared.featuretoggles.di

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.shared.featuretoggles.data.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.FeatureToggleUseCase
import de.stefan.lang.shapebyte.shared.featuretoggles.domain.LoadFeatureToggleUseCase
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
            factory<LoadFeatureToggleUseCase> {
                LoadFeatureToggleUseCase(
                    logger = get(),
                    repository = get(),
                    coroutineScopeProviding = get(),
                    coroutineContextProviding = get(),
                ) 
            }
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
    FeatureTogglesModuleProviding {

    override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase = get()
    override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase = get(parameters = { parametersOf(featureId) })
}
