package de.stefan.lang.shapebyte.features.workout.item.core.domain

import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: test pause
// TODO: run tests in general

class ItemsExecution(
    val items: List<ItemExecuting<*, *>>,
    override val logger: Logging,
) : Loggable {

    private val _state = MutableStateFlow<ItemsExecutionState>(ItemsExecutionState.Idle)
    val state: StateFlow<ItemsExecutionState> get() = _state

    private var currItemIndex = -1
    private val allSets: List<ItemSet> = items.flatMap { it.sets }
    private val setCount: Int = allSets.size

    private var setsFinished: Int = -1

    private var currJob: Job? = null
    private var pauseExecution: Boolean = false

    fun start(scope: CoroutineScope): Boolean {
        when (state.value) {
            ItemsExecutionState.Idle -> {
                _state.value = ItemsExecutionState.Started
                logD("ItemsExecution started")

                launchIn(scope) {
                    continueExecuting(scope)
                }

                return true
            }

            is ItemsExecutionState.Paused -> {
                pauseExecution = false
                val currItem = currItem() ?: run {
                    logW("Cannot start after pause, currItem is null")
                    return false
                }

                launchIn(scope) {
                    executeItem(currItem, scope)
                }

                return true
            }

            else -> {
                logW("ItemsExecution is already running")
                return false
            }
        }
    }

    fun pause(): Boolean {
        if (state.value !is ItemsExecutionState.Running) {
            logW("Cannot pause a set that is not running")
            return false
        }

        when (val currItem = currItem()) {
            null -> {
                logW("Cannot pause, currItem is null")
                return false
            }
            else -> {
                currItem.pause()
                invalidateJob()

                pauseExecution = true
                _state.value = ItemsExecutionState.Paused

                return true
            }
        }
    }

    fun pauseOrStart(scope: CoroutineScope): Boolean {
        return when (state.value) {
            is ItemsExecutionState.Running -> {
                pause()
            }

            else -> {
                start(scope)
            }
        }
    }

    private fun launchIn(
        scope: CoroutineScope,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        invalidateJob()
        currJob = scope.launch(block = block)
    }

    private suspend fun continueExecuting(scope: CoroutineScope) {
        if (pauseExecution) {
            return
        }

        val nextItem = nextItem()
        if (nextItem == null) {
            finish()
        } else {
            executeItem(nextItem, scope)
        }
    }

    private suspend fun executeItem(item: ItemExecuting<*, *>, scope: CoroutineScope) {
        if (!item.start(scope)) {
            logW("Unable to start $item, switch to next")
            continueExecuting(scope)

            return
        }

        item.state.collectLatest {
            if (it is ItemExecutionState.Running) {
                handleItemStateRunning(it)
            } else if (it is ItemExecutionState.Finished) {
                continueExecuting(scope)
            }
        }
    }

    private fun handleItemStateRunning(itemState: ItemExecutionState.Running<*>) {
        (itemState as? ItemExecutionState.SetStarted<*>)?.let {
            setsFinished += 1
        }

        val completed = Progress.with(setsFinished, setCount)
        val running = Progress((1f / setCount.toFloat()) * itemState.progress.value)
        val totalProgress = completed + running

        _state.value = ItemsExecutionState.Running(
            item = itemState.item,
            itemIndex = currItemIndex,
            itemCount = items.count(),
            itemState = itemState,
            totalProgress = totalProgress,
        )
    }

    private fun finish() {
        pauseExecution = false
        _state.value = ItemsExecutionState.Finished

        invalidateJob()
    }

    private fun currItem(): ItemExecuting<*, *>? {
        return items.getOrNull(currItemIndex)
    }

    private fun nextItem(): ItemExecuting<*, *>? {
        currItemIndex++
        return items.getOrNull(currItemIndex)
    }

    private fun invalidateJob() {
        currJob?.cancel()
        currJob = null
    }
}
