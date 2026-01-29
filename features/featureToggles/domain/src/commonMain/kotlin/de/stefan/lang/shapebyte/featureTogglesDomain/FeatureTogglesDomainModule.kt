package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.FeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.LoadFeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.FeatureTogglesDataModule
import de.stefan.lang.shapebyte.featureTogglesData.impl.DefaultFeatureToggleDatasourceImpl
import de.stefan.lang.shapebyte.featureTogglesData.impl.FeatureToggleDatasourceMock
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureToggleUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureTogglesDomainContract
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

object FeatureTogglesDomainModule :
    RootModule(
        bindings = ModuleBindings(
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
        dependencies = listOf(
            LoggingModule,
            FeatureTogglesDataModule,
        ),
    ),
    FeatureTogglesDomainContract {
    override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase = get()
    override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase =
        get(parameters = { parametersOf(featureId) })
}
