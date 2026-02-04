package de.stefan.lang.foundation.core.contract.usecase

import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.foundation.core.contract.loadstate.asDataFlow
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

// TODO: scope and dispatcher must be injected
public abstract class BaseDataUseCase<T> (
    override val logger: Logger,
) : Loggable {
    protected val mutableFlow: MutableSharedFlow<LoadState<T>> = MutableSharedFlow<LoadState<T>>(replay = 1)

    public val flow: SharedFlow<LoadState<T>> get() = mutableFlow
    public val dataFlow: Flow<T> = flow.asDataFlow()
}
