package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.core.di.ModuleBindings
import de.stefan.lang.core.di.RootModule
import de.stefan.lang.coroutines.CoreCoroutinesModule
import de.stefan.lang.shapebyte.featureToggles.data.FeatureTogglesDataModule
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.FeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureToggles.domain.implementation.LoadFeatureToggleUseCaseImpl
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureToggleUseCase
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.FeatureTogglesDomainContract
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.shapebyte.features.featureToggles.domain.generated.GeneratedDependencies
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
                        logger = LoggingModule.logger(),
                        repository = FeatureTogglesDataModule.featureToggleRepository(),
                        coroutineScopeProviding = CoreCoroutinesModule.coroutineScopeProvider(),
                        coroutineContextProviding = CoreCoroutinesModule.coroutineContextProvider(),
                    )
                }
            },
        ),
        dependencies = GeneratedDependencies.modules,
    ),
    FeatureTogglesDomainContract {
    override fun loadFeatureToggleUseCase(): LoadFeatureToggleUseCase = get()
    override fun featureToggleUseCase(featureId: String): FeatureToggleUseCase =
        get(parameters = { parametersOf(featureId) })
}
