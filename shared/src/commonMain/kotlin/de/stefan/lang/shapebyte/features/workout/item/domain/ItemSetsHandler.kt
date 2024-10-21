package de.stefan.lang.shapebyte.features.workout.item.domain

import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.data.ItemSetState
import de.stefan.lang.shapebyte.utils.Progress
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class ItemSetsHandler(
    override val logger: Logging,
    private val timedSetHandler: TimedItemSetHandler,
    private val defaultSetHandler: DefaultItemSetHandler,
) : Loggable {
    private var sets: List<ItemSet>? = null
    private var currSetHandler: ItemSetHandling? = null
    private var currSetIndex: Int = -1
    private val _stateFlow: MutableStateFlow<ItemSetsState> =
        MutableStateFlow<ItemSetsState>(ItemSetsState.Idle)

    val stateFlow: MutableStateFlow<ItemSetsState> = _stateFlow
    private var stateFlowJob: Job? = null

    fun start(sets: List<ItemSet>, scope: CoroutineScope) {
        if (this.sets == sets) {
            return
        }

        this.sets = sets
        currSetIndex = -1

        _stateFlow.value = ItemSetsState.Started(totalSets = sets.count())
        startNextSet(scope)
    }

    private fun startNextSet(scope: CoroutineScope) {
        val nextSetIndex = currSetIndex + 1
        val sets = this.sets ?: run {
            logW("(startNextSet) No sets to start")
            emptyList()
        }

        if (nextSetIndex == sets.count()) {
            finish()
            return
        }

        val nextSet = sets[nextSetIndex]
        currSetIndex = nextSetIndex

        startCoordinationFor(nextSet, scope) { data, scope ->
            handleSetStateReceived(data, scope)
        }
    }

    private fun finish() {
        this.currSetHandler = null
        this.currSetIndex = -1
        this.sets = emptyList()

        this._stateFlow.value = ItemSetsState.Finished
    }

    private fun startCoordinationFor(
        itemSet: ItemSet,
        scope: CoroutineScope,
        onReceivedState: suspend (ItemSetState, CoroutineScope) -> Unit,
    ) {
        val nextHandler: ItemSetHandling = when (itemSet) {
            is ItemSet.Timed -> timedSetHandler
            else -> defaultSetHandler
        }

        if (currSetHandler !== nextHandler) {
            stateFlowJob?.cancel()
            stateFlowJob = null
            currSetHandler = nextHandler

            stateFlowJob = scope.launch {
                nextHandler.stateFlow.collect { state ->
                    onReceivedState(state, this)

                    if (!coroutineContext.isActive || stateFlow.value is ItemSetsState.Finished) {
                        cancel()
                    }

                    yield()
                }
            }
        }

        nextHandler.start(set = itemSet, scope)
    }

    private fun handleSetStateReceived(state: ItemSetState, scope: CoroutineScope) {
        val nextSetIndex = currSetIndex + 1
        val sets = this.sets ?: return

        when (state) {
            is ItemSetState.Idle -> {}

            is ItemSetState.Started -> {
                logD("Started set $currSetIndex")
                _stateFlow.value = ItemSetsState.Running.SetStarted(
                    currentSet = currSetIndex,
                    totalSets = sets.count(),
                    setData = state.setData,
                )
            }

            is ItemSetState.Running -> {
                logD("Running set $currSetIndex")
                _stateFlow.value = ItemSetsState.Running.SetRunning(
                    currentSet = currSetIndex,
                    totalSets = sets.count(),
                    currentSetProgress = state.setData.progress,
                    totalProgress = totalProgress(currentSetProgress = state.setData.progress),
                    setData = state.setData,
                )
            }

            is ItemSetState.Paused -> {
                logD("Pausing set $currSetIndex")
                _stateFlow.value = ItemSetsState.Paused
            }

            is ItemSetState.Finished -> {
                logD("Finished set $currSetIndex")
                _stateFlow.value = ItemSetsState.Running.SetFinished(
                    currentSet = currSetIndex,
                    totalSets = sets.count(),
                    setData = state.setData,
                )

                if (nextSetIndex >= sets.count()) {
                    finish()
                } else {
                    startNextSet(scope)
                }
            }
        }
    }

    private fun totalProgress(currentSetProgress: Progress): Progress {
        if (currSetIndex < 0) {
            return Progress.ZERO
        }

        val sets = this.sets ?: return Progress.ZERO
        val total = sets.count() * Progress.ABSOLUTE
        val processedSets = currSetIndex * Progress.ABSOLUTE

        val totalProgress = processedSets + currentSetProgress.absoluteValue
        val totalProgressRelative = (totalProgress.toFloat() / total.toFloat())

        val retVal = Progress(totalProgressRelative)
        return retVal
    }
}
