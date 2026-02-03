package de.stefan.lang.foundation.presentation.contract.viewmodel

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.foundation.presentation.contract.event.UIEvent
import de.stefan.lang.foundation.presentation.contract.event.UIEventTransmitting
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.foundation.presentation.contract.state.UIStateProviding
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect abstract class BaseViewModel(
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
) : Loggable, UIStateProviding, UIEventTransmitting {
    protected val scope: CoroutineScope

    override val logger: Logger

    abstract override val state: StateFlow<UIState>

    protected val mutableEventFlow: MutableSharedFlow<UIEvent>
    override val eventFlow: SharedFlow<UIEvent>

    protected val coroutineContextProvider: CoroutineContextProvider
}
