package de.stefan.lang.shapebyte.featureTogglesDomain.impl

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.coroutines.contract.CoroutineScopeProviding
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.featureToggles.api.LoadFeatureToggleUseCase
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.usecase.BaseDataUseCase
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal class LoadFeatureToggleUseCaseImpl(
    private val repository: FeatureToggleRepository,
    override val logger: Logging,
    coroutineScopeProviding: CoroutineScopeProviding,
    coroutineContextProviding: CoroutineContextProviding,
) : BaseDataUseCase<FeatureToggle>(logger), LoadFeatureToggleUseCase {

    private val dispatcher = coroutineContextProviding.iODispatcher()
    private val scope = coroutineScopeProviding.createCoroutineScope(dispatcher)

    override operator fun invoke(identifier: String): Flow<LoadState<FeatureToggle>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)
            mutableFlow.emit(repository.fetchFeatureToggle(identifier))
        }

        return flow
    }
}
