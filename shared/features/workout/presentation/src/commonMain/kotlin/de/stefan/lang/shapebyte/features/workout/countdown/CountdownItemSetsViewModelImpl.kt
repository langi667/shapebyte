package de.stefan.lang.shapebyte.features.workout.countdown

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.foundationUi.api.state.UIState
import de.stefan.lang.shapebyte.features.workout.api.countdown.CountdownItemSetsUIIntent
import de.stefan.lang.shapebyte.features.workout.api.countdown.CountdownItemSetsViewData
import de.stefan.lang.shapebyte.features.workout.api.countdown.CountdownItemSetsViewModel
import de.stefan.lang.shapebyte.features.workout.api.item.Item
import de.stefan.lang.shapebyte.features.workout.api.item.ItemSet
import de.stefan.lang.shapebyte.features.workout.api.item.None
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.ItemExecutionState
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecution
import de.stefan.lang.shapebyte.features.workout.workoutDomain.item.TimedItemExecutionData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountdownItemSetsViewModelImpl(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
    private val timedHandlerFactory: (item: Item, sets: List<ItemSet.Timed.Seconds>) -> TimedItemExecution,
) : CountdownItemSetsViewModel(logger, coroutineContextProvider) {
    companion object Companion {
        private const val TIMER_OFFSET = 100L
    }

    private val _state: MutableStateFlow<UIState.Data<CountdownItemSetsViewData>> = MutableStateFlow(
        UIState.Data(
            CountdownItemSetsViewData(),
        ),
    )
    
    override val state: StateFlow<UIState.Data<CountdownItemSetsViewData>> = _state
    private var itemSets: List<ItemSet.Timed.Seconds>? = null

    override fun intent(intent: CountdownItemSetsUIIntent) {
        when (intent) {
            is CountdownItemSetsUIIntent.Start -> {
                start(intent.itemSets)
            }
        }
    }

    // TODO: map to intent
    private fun start(itemSets: List<ItemSet.Timed.Seconds>) {
        // TODO: check if started already
        this.itemSets = itemSets

        val timedHandler = timedHandlerFactory(None, itemSets)
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
