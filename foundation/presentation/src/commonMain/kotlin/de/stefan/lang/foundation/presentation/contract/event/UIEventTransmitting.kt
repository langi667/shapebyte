package de.stefan.lang.foundation.presentation.contract.event

import kotlinx.coroutines.flow.SharedFlow

interface UIEventTransmitting {
    val eventFlow: SharedFlow<UIEvent>
}
