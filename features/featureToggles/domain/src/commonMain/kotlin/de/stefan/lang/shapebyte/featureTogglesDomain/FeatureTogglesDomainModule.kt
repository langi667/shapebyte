package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.shapebyte.featureToggles.domain.implementation.FeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.LoadFeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureToggleUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureTogglesDomainContract
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.featureToggles.domain.generated.Dependencies
import de.stefan.lang.shapebyte.features.featureToggles.domain.generated.Module
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

public object FeatureTogglesDomainModule :
    Module(
        globalBindings = {
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
                    coroutineScopeProvider = Dependencies.coroutineScopeProvider(),
                    coroutineContextProvider = Dependencies.coroutineContextProvider(),
                )
            }
        },
    ),
    FeatureTogglesDomainContract {
    public override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase = get()
    public override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase =
        get(
            parameters = {
                parametersOf(featureId)
            },
        )
}
