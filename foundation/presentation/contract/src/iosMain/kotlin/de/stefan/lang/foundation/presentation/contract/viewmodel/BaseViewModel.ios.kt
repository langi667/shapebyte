package de.stefan.lang.foundation.presentation.contract.viewmodel

import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.foundation.presentation.contract.event.UIEvent
import de.stefan.lang.foundation.presentation.contract.event.UIEventTransmitting
import de.stefan.lang.foundation.presentation.contract.state.UIStateProviding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.buffer

public actual abstract class BaseViewModel actual constructor(
    actual override val logger: Logger,
    public actual val coroutineContextProvider: CoroutineContextProvider
) : Loggable, UIStateProviding, UIEventTransmitting {

    protected actual val scope: CoroutineScope = CoroutineScope(
        context = coroutineContextProvider.mainImmediateDispatcher() + SupervisorJob()
    )

    protected actual val mutableEventFlow: MutableSharedFlow<UIEvent> = MutableSharedFlow<UIEvent>().apply {
        this.buffer(0, BufferOverflow.DROP_OLDEST)
    }

    actual override val eventFlow: SharedFlow<UIEvent> = mutableEventFlow

    protected fun clear() {
        scope.cancel()
    }
}
