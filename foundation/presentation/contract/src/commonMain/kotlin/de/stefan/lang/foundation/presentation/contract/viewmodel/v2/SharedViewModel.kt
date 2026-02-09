package de.stefan.lang.foundation.presentation.contract.viewmodel.v2;

import de.stefan.lang.foundation.presentation.contract.event.UIEventProvider
import de.stefan.lang.foundation.presentation.contract.event.UIEventUpdater
import de.stefan.lang.foundation.presentation.contract.intent.UIIntentHandler
import de.stefan.lang.foundation.presentation.contract.state.UIStateProvider
import de.stefan.lang.foundation.presentation.contract.state.UIStateUpdater
import de.stefan.lang.utils.logging.contract.Loggable

public interface SharedViewModel:
    Loggable,
    UIStateProvider,
    UIStateUpdater,
    UIEventProvider,
    UIIntentHandler,
    UIEventUpdater {

    public fun clear()
}