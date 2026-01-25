package de.stefan.lang.foundationPresentation.api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefan.lang.coreutils.contract.logging.Loggable
import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundationPresentation.api.event.UIEvent
import de.stefan.lang.foundationPresentation.api.event.UIEventTransmitting
import de.stefan.lang.foundationPresentation.api.state.UIStateProviding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.buffer

actual abstract class BaseViewModel actual constructor(
    actual override val logger: Logging,
    actual val coroutineContextProvider: CoroutineContextProviding,
) : ViewModel(), Loggable, UIStateProviding, UIEventTransmitting {
    actual val scope: CoroutineScope = viewModelScope

    protected actual val mutableEventFlow = MutableSharedFlow<UIEvent>().apply {
        this.buffer(0, BufferOverflow.DROP_OLDEST)
    }

    actual override val eventFlow: SharedFlow<UIEvent> = mutableEventFlow
}
