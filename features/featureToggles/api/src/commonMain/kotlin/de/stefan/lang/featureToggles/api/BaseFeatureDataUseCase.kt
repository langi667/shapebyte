package de.stefan.lang.featureToggles.api

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.loadstate.asDataFlow
import de.stefan.lang.foundation.core.contract.usecase.BaseDataUseCase
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Base class for data fetching use cases that depend on a feature toggle.
 *
 * Will check if the toggle is enabled or disabled and calls respective blocks. Basically use
 * collectFromFeatureToggle in your invoke method and pass a block that should be executed.
 *
 * For in case the toggle is disabled, you can emit an error with emitError(error: Throwable).
 */
open class BaseFeatureDataUseCase<T>(
    val featureToggle: String, // TODO: make optional
    protected val scope: CoroutineScope,
    protected val dispatcher: CoroutineDispatcher,
    private val loadFeatureToggleUseCase: LoadFeatureToggleUseCase,
    logger: Logging,
) : BaseDataUseCase<T>(logger) {

    private val featureEnabled: Flow<Boolean> by lazy {
        loadFeatureToggleUseCase.invoke(featureToggle)
            .asDataFlow()
            .map {
                it.isEnabled
            }
    }

    /**
     * Call this in your subclass (if exists). It will check if the feature toggle is enabled or
     * disabled. In case of enabled, it will call the onEnabled block. In case of disabled, it will
     * call the onDisabled block.
     *
     * @param onDisabled the block where you can create an error in case the feature is disabled.
     * Return something like MyFeatureError.FeatureDisabled
     * @param onEnabled the block is called if the feature is enabled. Create and return data here.
     * For example, call the repository and return the result.
     */
    operator fun invoke(
        onDisabled: () -> Throwable,
        onEnabled: suspend () -> LoadState.Result<T>,
    ): SharedFlow<LoadState<T>> {
        scope.launch(dispatcher) {
            mutableFlow.emit(LoadState.Loading)
            collectFromFeatureToggle(scope) { enabled ->
                if (enabled) {
                    val result = onEnabled()
                    mutableFlow.emit(result)
                } else {
                    val error = onDisabled()
                    emitError(error)
                }
            }
        }

        return flow
    }

    protected fun collectFromFeatureToggle(
        scope: CoroutineScope,
        block: suspend (Boolean) -> Unit,
    ) = scope.launch {
        featureEnabled.collectLatest { isEnabled ->
            block(isEnabled)
        }
    }

    protected suspend fun emitError(error: Throwable) {
        mutableFlow.emit(LoadState.Error(error))
    }
}
