package de.stefan.lang.foundationUI.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.foundationUI.event.UIEvent
import de.stefan.lang.foundationUI.event.UIEventTransmitting
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
