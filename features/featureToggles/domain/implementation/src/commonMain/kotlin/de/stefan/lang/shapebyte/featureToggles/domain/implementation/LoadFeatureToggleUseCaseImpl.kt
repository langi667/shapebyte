package de.stefan.lang.shapebyte.featureToggles.domain.implementation

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.usecase.BaseDataUseCase
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleRepository
import de.stefan.lang.shapebyte.featureTogglesDomain.contract.LoadFeatureToggleUseCase
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

public class LoadFeatureToggleUseCaseImpl(
    private val repository: FeatureToggleRepository,
    override val logger: Logger,
    coroutineScopeProvider: CoroutineScopeProvider,
    coroutineContextProvider: CoroutineContextProvider,
) : BaseDataUseCase<FeatureToggle>(logger), LoadFeatureToggleUseCase {

    private val dispatcher = coroutineContextProvider.iODispatcher()
    private val scope = coroutineScopeProvider.createCoroutineScope(dispatcher)

    override operator fun invoke(identifier: String): Flow<LoadState<FeatureToggle>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)
            mutableFlow.emit(repository.fetchFeatureToggle(identifier))
        }

        return flow
    }
}
