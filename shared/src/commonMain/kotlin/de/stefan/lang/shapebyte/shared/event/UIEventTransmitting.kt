package de.stefan.lang.shapebyte.shared.event

import kotlinx.coroutines.flow.SharedFlow

interface UIEventTransmitting {
    val eventFlow: SharedFlow<UIEvent>
}
