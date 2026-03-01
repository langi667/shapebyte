package de.stefan.lang.foundation.presentation.contract.viewmodel

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.presentation.contract.event.UIEvent
import de.stefan.lang.foundation.presentation.contract.intent.UIIntent
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TODO: rename to BaseViewModel, once the current BaseViewModel is removed
public open class BaseViewModel<INTENT : UIIntent> (
    override val logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) : SharedViewModel {
    /**
     * Override to perform cleanup when the ViewModel is cleared.
     * This can be used to cancel any ongoing operations or release resources.
     */
    protected open fun onCleared() {
    }

    protected val scope: CoroutineScope by lazy {
        coroutineScopeProvider.createCoroutineScope(
            context = SupervisorJob() + coroutineContextProvider.mainImmediateDispatcher(),
        )
    }

    final override val state: StateFlow<UIState>
        field = MutableStateFlow<UIState>(UIState.Idle)

    final override val eventFlow: SharedFlow<UIEvent>
        field = MutableSharedFlow<UIEvent>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

    final override fun updateState(newState: UIState) {
        state.value = newState
    }

    final override fun emitEvent(event: UIEvent) {
        scope.launch {
            eventFlow.emit(event)
        }
    }

    final override fun clear() {
        scope.cancel()
        onCleared()
    }

    @Suppress("UNCHECKED_CAST")
    final override fun <T : UIIntent> handleIntent(
        event: T,
    ) {
        val castedEvent = event as? INTENT

        if (castedEvent == null) {
            logE("Received intent of type ${event::class} is not expected event, Ignoring intent.")
            return
        } else {
            onIntent(castedEvent)
        }
    }

    protected open fun onIntent(intent: INTENT) {
    }
}
