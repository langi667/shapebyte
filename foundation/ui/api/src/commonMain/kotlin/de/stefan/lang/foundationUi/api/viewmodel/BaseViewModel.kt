package de.stefan.lang.foundationUi.api.viewmodel

import de.stefan.lang.coreutils.api.logging.Loggable
import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.foundationUi.api.event.UIEvent
import de.stefan.lang.foundationUi.api.event.UIEventTransmitting
import de.stefan.lang.foundationUi.api.state.UIState
import de.stefan.lang.foundationUi.api.state.UIStateProviding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect abstract class BaseViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : Loggable, UIStateProviding, UIEventTransmitting {
    val scope: CoroutineScope

    override val logger: Logging

    abstract override val state: StateFlow<UIState>

    protected val mutableEventFlow: MutableSharedFlow<UIEvent>
    override val eventFlow: SharedFlow<UIEvent>

    protected val coroutineContextProvider: CoroutineContextProviding
}
