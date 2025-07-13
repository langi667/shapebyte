package de.stefan.lang.foundationUi.api.event

import kotlinx.coroutines.flow.SharedFlow

interface UIEventTransmitting {
    val eventFlow: SharedFlow<UIEvent>
}
