package de.stefan.lang.foundationPresentation.api.event

import kotlinx.coroutines.flow.SharedFlow

interface UIEventTransmitting {
    val eventFlow: SharedFlow<UIEvent>
}
