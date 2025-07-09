package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureTogglesData.impl.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface FeatureTogglesDomainModuleProviding {
    fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase
    fun featureToggleUseCase(featureId: String): FeatureToggleUseCase
}

object FeatureTogglesDomainModule :
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
    FeatureTogglesDomainModuleProviding {
    override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase = get()
    override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase = get(parameters = { parametersOf(featureId) })
}
