package de.stefan.lang.foundationUI.viewmodel

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.foundationUI.event.UIEvent
import de.stefan.lang.foundationUI.event.UIEventTransmitting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

actual abstract class BaseViewModel actual constructor(
    actual override val logger: Logging,
    actual val coroutineContextProvider: CoroutineContextProviding
) : Loggable, UIStateProvider, UIEventTransmitting {

    actual val scope: CoroutineScope = CoroutineScope(
        context = coroutineContextProvider.mainImmediateDispatcher() + SupervisorJob()
    )

    protected val mutableEventFlow = MutableSharedFlow<UIEvent>()
    actual override val eventFlow: SharedFlow<UIEvent> = mutableEventFlow

    fun clear() {
        scope.cancel()
    }
}