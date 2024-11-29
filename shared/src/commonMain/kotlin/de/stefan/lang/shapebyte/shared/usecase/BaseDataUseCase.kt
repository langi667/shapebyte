package de.stefan.lang.shapebyte.shared.usecase

import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.loading.data.asDataFlow
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
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
