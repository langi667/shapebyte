package de.stefan.lang.shapebyte.shared.featuretoggles.domain

import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.coroutines.CoroutineScopeProviding
import de.stefan.lang.core.logging.Logging
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleRepository
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.usecase.BaseDataUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoadFeatureToggleUseCase(
    private val repository: FeatureToggleRepository,
    override val logger: Logging,
    coroutineScopeProviding: CoroutineScopeProviding,
    coroutineContextProviding: CoroutineContextProviding,
) : BaseDataUseCase<FeatureToggle>(logger) {

    private val dispatcher = coroutineContextProviding.iODispatcher()
    private val scope = coroutineScopeProviding.createCoroutineScope(dispatcher)

    operator fun invoke(identifier: String): Flow<LoadState<FeatureToggle>> {
        scope.launch {
            mutableFlow.emit(LoadState.Loading)
            mutableFlow.emit(repository.fetchFeatureToggle(identifier))
        }

        return flow
    }
}
