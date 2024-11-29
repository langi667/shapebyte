package de.stefan.lang.shapebyte.features.workout.item.core.domain

import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemsExecution(
    val items: List<ItemExecuting<*, *>>,
    override val logger: Logging,
) : Loggable {

    private val _state = MutableStateFlow<ItemsExecutionState>(ItemsExecutionState.Idle)
    val state: StateFlow<ItemsExecutionState> get() = _state

    private var currItemIndex = -1
    private val allSets: List<ItemSet> = items.flatMap { it.sets }
    private val setCount: Int = allSets.size
    private var setsFinished: Int = 0

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
        (itemState as? ItemExecutionState.SetFinished<*>)?.let {
            setsFinished += 1
        }

        _state.value = ItemsExecutionState.Running(
            itemState.item,
            currItemIndex,
            items.count(),
            itemState,
            Progress.with(setsFinished, setCount),
            Progress.with(setsFinished + 1, setCount),
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
