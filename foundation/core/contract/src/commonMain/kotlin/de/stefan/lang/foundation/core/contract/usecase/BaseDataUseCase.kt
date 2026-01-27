package de.stefan.lang.foundation.core.contract.usecase

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.loadstate.asDataFlow
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

// TODO: scope and dispatcher must be injected
abstract class BaseDataUseCase<T> (
    override val logger: Logging,
) : Loggable {
    protected val mutableFlow = MutableSharedFlow<LoadState<T>>(replay = 1)

    val flow: SharedFlow<LoadState<T>> get() = mutableFlow
    val dataFlow = flow.asDataFlow()
}
