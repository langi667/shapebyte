package de.stefan.lang.shapebyte.shared.viewmodel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import de.stefan.lang.foundation.ui.event.UIEvent
import de.stefan.lang.foundation.ui.event.UIEventTransmitting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

actual abstract class BaseViewModel actual constructor(
    actual override val logger: Logging,
    actual val coroutineContextProvider: CoroutineContextProviding,
) : ViewModel(), Loggable, UIStateProvider, UIEventTransmitting {
    actual val scope: CoroutineScope = viewModelScope

    protected val mutableEventFlow = MutableSharedFlow<UIEvent>()
    actual override val eventFlow: SharedFlow<UIEvent> = mutableEventFlow
}
