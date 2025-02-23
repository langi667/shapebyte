package de.stefan.lang.shapebyte.features.workout.item.timed.ui

import de.stefan.lang.coreCoroutinesProviding.CoroutineContextProviding
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.foundationUI.viewmodel.BaseViewModel
import de.stefan.lang.foundationUI.viewmodel.UIState
import de.stefan.lang.shapebyte.features.workout.WorkoutModule
import de.stefan.lang.shapebyte.features.workout.item.core.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.core.data.None
import de.stefan.lang.shapebyte.features.workout.item.core.domain.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.item.timed.domain.TimedItemExecutionData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TODO: there is an ItemSetsViewModel in original code, check if needed later
class CountdownItemSetsViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {
    companion object {
        private const val TIMER_OFFSET = 100L
    }

    private val _state: MutableStateFlow<UIState.Data<CountdownItemSetsViewData>> = MutableStateFlow(
        UIState.Data(
            CountdownItemSetsViewData(),
        ),
    )
    
    override val state: StateFlow<UIState.Data<CountdownItemSetsViewData>> = _state
    private var itemSets: List<ItemSet.Timed.Seconds>? = null

    fun start(itemSets: List<ItemSet.Timed.Seconds>) {
        // TODO: check if started already
        this.itemSets = itemSets

        val timedHandler = WorkoutModule.createTimedItemExecution(None, itemSets)
        timedHandler.start(scope)

        scope.launch {
            timedHandler.state.collect { state ->
                when (state) {
                    is ItemExecutionState.Started -> {
                        logD("Started")
                    }

                    is ItemExecutionState.Running -> {
                        handleStateRunning(state)
                    }

                    is ItemExecutionState.Finished -> {
                        logD("Finished")
                        /* fall through */
                    }
                    else -> { /* fall through */ }
                }
            }
        }
    }

    private suspend fun handleStateRunning(setsState: ItemExecutionState.Running<TimedItemExecutionData>) {
        when (setsState) {
            is ItemExecutionState.SetRunning -> {
                return
            }

            is ItemExecutionState.SetStarted -> {
                logI("Set started")
                handleSetStateStarted(setsState)
            }

            is ItemExecutionState.SetFinished -> {
                logI("Set finished")
                handleSetStateFinished()
            }
        }
    }

    private fun handleSetStateStarted(
        setsState: ItemExecutionState.SetStarted<TimedItemExecutionData>,
    ) {
        val setData = setsState.setData
        val countdownText = setData.totalTimeRemaining
            .toString()
            .replace(Regex("[^0-9]"), "") // TODO: formatting

        val animationDuration = setData.setDuration.inWholeMilliseconds - TIMER_OFFSET

        val state = CountdownItemSetsViewData(
            countdownText = countdownText,
            animationDuration = animationDuration.toInt(),
            scale = 3f,
            alpha = 0f,
        )

        _state.value = UIState.Data(state)
    }

    private suspend fun handleSetStateFinished() {
        val state = CountdownItemSetsViewData(
            countdownText = "",
            animationDuration = 0,
            scale = 1f,
            alpha = 1f,
        )

        _state.value = UIState.Data(state)
        delay(TIMER_OFFSET) // TODO: check if needed
    }
}
