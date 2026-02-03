package de.stefan.lang.shapebyte.features.workout.domain.implementation.item

import de.stefan.lang.coreutils.contract.progress.Progress
import de.stefan.lang.shapebyte.features.workout.data.contract.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecuting
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecution
import de.stefan.lang.shapebyte.features.workout.domain.contract.item.ItemsExecutionState
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemsExecutionImpl(
    override val items: List<ItemExecuting<*, *>>,
    override val logger: Logger,
) : ItemsExecution {

    private val _state = MutableStateFlow<ItemsExecutionState>(ItemsExecutionState.Idle)
    override val state: StateFlow<ItemsExecutionState> get() = _state

    private var currItemIndex = -1
    private val allSets: List<ItemSet> = items.flatMap { it.sets }
    private val setCount: Int = allSets.size

    private var setsFinished: Int = -1

    private var currJob: Job? = null
    private var pauseExecution: Boolean = false

    override fun start(scope: CoroutineScope): Boolean {
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

    override fun pause(): Boolean {
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

                val totalProgress = (state.value as? ItemsExecutionState.Running)?.totalProgress ?: Progress.ZERO

                pauseExecution = true
                _state.value = ItemsExecutionState.Paused(totalProgress)

                return true
            }
        }
    }

    override fun stop(): Boolean {
        if (state.value !is ItemsExecutionState.Launched) {
            logW("Cannot stop, items execution is not launched")
            return false
        }

        currItem()?.stop()
        invalidateJob()

        _state.value = ItemsExecutionState.Finished(false)
        return true
    }

    override fun pauseOrStart(scope: CoroutineScope): Boolean {
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
        _state.value = ItemsExecutionState.Finished(true)

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
