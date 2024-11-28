package de.stefan.lang.shapebyte.features.workout.item.shared.domain

import de.stefan.lang.shapebyte.features.workout.item.shared.data.Item
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed class ItemsExecutionState {
    data object Idle : ItemsExecutionState()

    // TODO: Progress
    data object Started : ItemsExecutionState()
    data class Running(
        val item: Item,
        val itemIndex: Int,
        val itemCount: Int,
        val itemState: ItemExecutionState.Running<*>,
    ) : ItemsExecutionState()
    data object Finished : ItemsExecutionState()
}

class ItemsExecution(
    val items: List<ItemExecuting<*, *>>,
    override val logger: Logging,
) : Loggable {

    private var currItemIndex = -1
    private val _state = MutableStateFlow<ItemsExecutionState>(ItemsExecutionState.Idle)
    val state: StateFlow<ItemsExecutionState> get() = _state

    fun start(scope: CoroutineScope): Boolean {
        if (state.value == ItemsExecutionState.Idle) {
            _state.value = ItemsExecutionState.Started
            logD("ItemsExecution started")
            continueExecuting(scope)

            return true
        } else {
            logW("ItemsExecution is already running")
            return false
        }
    }

    private fun continueExecuting(scope: CoroutineScope) {
        scope.launch {
            val nextItem = nextItem()
            if (nextItem == null) {
                finish()
            } else {
                if (!nextItem.start(scope)) {
                    logW("Unable to start $nextItem, switch to next")
                    continueExecuting(scope)

                    return@launch
                }

                nextItem.state
                    .collectLatest {
                        if (it is ItemExecutionState.Running) {
                            handleItemStateRunning(it)
                        } else if (it is ItemExecutionState.Finished) {
                            continueExecuting(scope)
                            cancel()
                        }
                    }
            }
        }
    }

    private fun handleItemStateRunning(itemState: ItemExecutionState.Running<*>) {
        _state.value = ItemsExecutionState.Running(
            itemState.item,
            currItemIndex,
            items.count(),
            itemState,
        )
    }

    private fun finish() {
        logD("Finished")
        _state.value = ItemsExecutionState.Finished
    }

    private fun nextItem(): ItemExecuting<*, *>? {
        currItemIndex++
        return items.getOrNull(currItemIndex)
    }
}
