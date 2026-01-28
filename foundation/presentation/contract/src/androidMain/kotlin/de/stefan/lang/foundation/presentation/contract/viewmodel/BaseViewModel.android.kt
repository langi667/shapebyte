package de.stefan.lang.foundation.presentation.contract.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.presentation.contract.event.UIEvent
import de.stefan.lang.foundation.presentation.contract.event.UIEventTransmitting
import de.stefan.lang.foundation.presentation.contract.state.UIStateProviding
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.buffer

public actual abstract class BaseViewModel actual constructor(
    actual override val logger: Logging,
    protected actual val coroutineContextProvider: CoroutineContextProviding,
) : ViewModel(), Loggable, UIStateProviding, UIEventTransmitting {
    protected actual val scope: CoroutineScope = viewModelScope

    protected actual val mutableEventFlow: MutableSharedFlow<UIEvent> = MutableSharedFlow<UIEvent>().apply {
        this.buffer(0, BufferOverflow.DROP_OLDEST)
    }

    actual override val eventFlow: SharedFlow<UIEvent> = mutableEventFlow
}
