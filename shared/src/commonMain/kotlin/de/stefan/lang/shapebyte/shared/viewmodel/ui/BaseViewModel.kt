package de.stefan.lang.shapebyte.shared.viewmodel.ui

import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import de.stefan.lang.foundation.ui.event.UIEvent
import de.stefan.lang.foundation.ui.event.UIEventTransmitting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect abstract class BaseViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : Loggable, UIStateProvider, UIEventTransmitting {
    val scope: CoroutineScope
    override val logger: Logging
    abstract override val state: StateFlow<UIState>
    override val eventFlow: SharedFlow<UIEvent>
    protected val coroutineContextProvider: CoroutineContextProviding
}
