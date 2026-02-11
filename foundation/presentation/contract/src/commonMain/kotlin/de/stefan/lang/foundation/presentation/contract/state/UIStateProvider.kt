package de.stefan.lang.foundation.presentation.contract.state

import kotlinx.coroutines.flow.StateFlow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

public interface UIStateProvider {
    public val state: StateFlow<UIState>
}

// TODO: join with UIStateProvider once refactoring is done
@OptIn(ExperimentalObjCName::class)
public interface UIStateUpdater {

    public fun updateState(
        @ObjCName("_")
        newState: UIState,
    )
}
