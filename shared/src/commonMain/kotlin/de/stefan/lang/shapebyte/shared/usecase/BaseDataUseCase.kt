package de.stefan.lang.shapebyte.shared.usecase

import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.shared.loading.data.asDataFlow
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseDataUseCase<T> (
    override val logger: Logging,
) : Loggable {
    protected val mutableFlow = MutableSharedFlow<LoadState<T>>()

    val flow: SharedFlow<LoadState<T>> get() = mutableFlow
    val dataFlow = flow.asDataFlow()
}
