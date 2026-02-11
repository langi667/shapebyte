package de.stefan.lang.foundation.presentation.contract.event

import kotlinx.coroutines.flow.SharedFlow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

public interface UIEventProvider {
    public val eventFlow: SharedFlow<UIEvent>
}

@OptIn(ExperimentalObjCName::class)
// TODO: join with UIEventProvider once refactoring is done
public interface UIEventUpdater {
    public fun emitEvent(
        @ObjCName("_")
        event: UIEvent,
    )
}
