package de.stefan.lang.shapebyte.shared.viewmodel.ui

import de.stefan.lang.shapebyte.shared.event.UIEvent
import de.stefan.lang.shapebyte.shared.event.UIEventTransmitting
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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