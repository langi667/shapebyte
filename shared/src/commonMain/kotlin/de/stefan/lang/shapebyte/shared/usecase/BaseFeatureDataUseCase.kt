package de.stefan.lang.shapebyte.shared.usecase

import de.stefan.lang.shapebyte.di.DPI
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
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
    private val featureToggle: String,
    logger: Logging,
) : BaseDataUseCase<T>(logger) {

    private val featureEnabled: Flow<Boolean> by lazy {
        val featureToggleUseCase = DPI.featureToggleUseCase(featureToggle)
        featureToggleUseCase.isEnabled
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
