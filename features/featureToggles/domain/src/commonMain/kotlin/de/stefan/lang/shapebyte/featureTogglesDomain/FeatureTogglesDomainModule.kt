package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.FeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.LoadFeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureToggleUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureTogglesDomainContract
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.featureToggles.domain.generated.Dependencies
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
                        logger = Dependencies.logger(),
                        repository = Dependencies.featureToggleRepository(),
                        coroutineScopeProviding = Dependencies.coroutineScopeProvider(),
                        coroutineContextProviding = Dependencies.coroutineContextProvider(),
                    )
                }
            },
        ),
        dependencies = Dependencies.modules,
    ),
    FeatureTogglesDomainContract {
    override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase = get()
    override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase =
        get(
            parameters = {
                parametersOf(featureId)
            },
        )
}
