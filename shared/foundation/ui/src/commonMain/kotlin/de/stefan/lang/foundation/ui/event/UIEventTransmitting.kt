package de.stefan.lang.foundation.ui.event

import kotlinx.coroutines.flow.SharedFlow

interface UIEventTransmitting {
    val eventFlow: SharedFlow<UIEvent>
}
