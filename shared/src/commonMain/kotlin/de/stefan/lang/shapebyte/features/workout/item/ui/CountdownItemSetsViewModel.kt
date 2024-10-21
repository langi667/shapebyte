package de.stefan.lang.shapebyte.features.workout.item.ui

import de.stefan.lang.shapebyte.features.workout.item.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.item.domain.ItemSetsHandler
import de.stefan.lang.shapebyte.features.workout.item.domain.ItemSetsState
import de.stefan.lang.shapebyte.shared.viewmodel.ui.BaseViewModel
import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TODO: there is an ItemSetsViewModel in original code, check if needed later
class CountdownItemSetsViewModel(
    private val itemSetsHandler: ItemSetsHandler,
    logger: Logging,
) : BaseViewModel(logger) {
    companion object {
        private const val TIMER_OFFSET = 100L
    }

    private val _state: MutableStateFlow<UIState.Data<CountdownItemSetsViewData>> = MutableStateFlow(
        UIState.Data(
            CountdownItemSetsViewData(),
        ),
    )
    
    override val state: StateFlow<UIState.Data<CountdownItemSetsViewData>> = _state

    private var itemSets: List<ItemSet.Timed>? = null

    fun start(itemSets: List<ItemSet.Timed>) {
        // TODO: check if started already
        this.itemSets = itemSets

        scope.launch {
            itemSetsHandler.stateFlow.collect { state ->
                when (state) {
                    is ItemSetsState.Started -> {
                        logD("Started")
                    }

                    is ItemSetsState.Running -> {
                        handleStateRunning(state)
                    }

                    else -> {
                        /* fall through */
                    }
                }
            }
        }

        itemSetsHandler.start(itemSets, this.scope)
    }

    private suspend fun handleStateRunning(setsState: ItemSetsState.Running) {
        when (setsState) {
            is ItemSetsState.Running.SetRunning -> {
                return
            }

            is ItemSetsState.Running.SetStarted -> {
                handleSetStateStarted(setsState)
            }

            is ItemSetsState.Running.SetFinished -> {
                handleSetStateFinished()
            }
        }

        if (setsState.isRunning) {
            return
        }
    }

    private fun handleSetStateStarted(setsState: ItemSetsState.Running.SetStarted) {
        val itemSets = this.itemSets ?: return
        val itemSet = itemSets.getOrNull(setsState.currentSet) ?: return

        val countdownText = (itemSets.count() - setsState.currentSet).toString() // TODO: formatting
        val animationDuration = itemSet.duration.inWholeMilliseconds - TIMER_OFFSET

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
