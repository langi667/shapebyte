package de.stefan.lang.foundation.presentation.contract.event

import kotlinx.coroutines.flow.SharedFlow

public interface UIEventTransmitting {
    public val eventFlow: SharedFlow<UIEvent>
}
