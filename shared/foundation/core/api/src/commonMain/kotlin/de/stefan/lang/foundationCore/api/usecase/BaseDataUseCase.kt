package de.stefan.lang.foundationCore.api.usecase

import de.stefan.lang.coreutils.api.Loggable
import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.foundationCore.api.loadstate.asDataFlow
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
