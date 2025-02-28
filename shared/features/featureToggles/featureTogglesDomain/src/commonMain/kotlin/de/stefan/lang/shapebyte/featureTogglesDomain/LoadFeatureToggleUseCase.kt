package de.stefan.lang.shapebyte.featureTogglesDomain

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreCoroutinesProviding.CoroutineScopeProviding
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.foundationCore.loadstate.LoadState
import de.stefan.lang.foundationCore.usecase.BaseDataUseCase
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggle
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleRepository
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
