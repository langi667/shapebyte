package de.stefan.lang.foundationUI.viewmodel

import de.stefan.lang.coreutils.api.Loggable
import de.stefan.lang.coreutils.api.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.foundationUI.event.UIEvent
import de.stefan.lang.foundationUI.event.UIEventTransmitting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.buffer

actual abstract class BaseViewModel actual constructor(
    actual override val logger: Logging,
    actual val coroutineContextProvider: CoroutineContextProviding
) : Loggable, UIStateProvider, UIEventTransmitting {

    actual val scope: CoroutineScope = CoroutineScope(
        context = coroutineContextProvider.mainImmediateDispatcher() + SupervisorJob()
    )

    protected actual val mutableEventFlow = MutableSharedFlow<UIEvent>().apply {
        this.buffer(0, BufferOverflow.DROP_OLDEST)
    }

    actual override val eventFlow: SharedFlow<UIEvent> = mutableEventFlow

    fun clear() {
        scope.cancel()
    }
}