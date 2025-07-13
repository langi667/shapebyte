package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.featureToggles.api.FeatureToggleUseCase
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureTogglesData.FeatureTogglesDataModule
import de.stefan.lang.shapebyte.featureTogglesData.impl.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.featureTogglesDomain.impl.FeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureTogglesDomain.impl.LoadFeatureToggleUseCaseImpl
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

interface FeatureTogglesDomainModuleProviding {
    fun featureTogglesLoader(): LoadFeatureToggleUseCase
    fun featureToggleUseCase(featureId: String): FeatureToggleUseCase
}

object FeatureTogglesDomainModule :
    RootDIModule(
        providedModule = DIModuleDeclaration(
            allEnvironments = {
                factory<FeatureToggleUseCase> { (featureId: String) ->
                    FeatureToggleUseCaseImpl(
                        featureId = featureId,
                        loadFeatureToggleUseCase = get(),
                    )
                }
                factory<LoadFeatureToggleUseCase> {
                    LoadFeatureToggleUseCaseImpl(
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
        diModules = listOf(
            FeatureTogglesDataModule,
        ),
    ),
    FeatureTogglesDomainModuleProviding {
    override fun featureTogglesLoader(): LoadFeatureToggleUseCase = get()
    override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase =
        get(parameters = { parametersOf(featureId) })
}
