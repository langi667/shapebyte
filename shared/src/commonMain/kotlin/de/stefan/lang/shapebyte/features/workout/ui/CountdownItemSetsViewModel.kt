package de.stefan.lang.shapebyte.features.workout.ui

import de.stefan.lang.shapebyte.features.workout.data.ItemSet
import de.stefan.lang.shapebyte.features.workout.domain.ItemSetsHandler
import de.stefan.lang.shapebyte.features.workout.domain.ItemSetsState
import de.stefan.lang.shapebyte.shared.ui.BaseViewModel
import de.stefan.lang.shapebyte.utils.Logging
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// TODO: there is an ItemSetsViewModel in original code, check if needed later
// TODO: Test
class CountdownItemSetsViewModel(
    private val itemSetsHandler: ItemSetsHandler,
    logger: Logging,
) : BaseViewModel(logger) {
    companion object {
        private const val timerOffset = 100L
    }

    data class UIState(
        val countdownText: String = "",
        val scale: Float = 1f,
        val animationDuration: Int = 0,
        val alpha: Float = 1f,
    )

    private val _state = MutableStateFlow(UIState())
    val state: StateFlow<UIState> = _state

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
        val animationDuration = itemSet.duration.inWholeMilliseconds - timerOffset

        val state = UIState(
            countdownText = countdownText,
            animationDuration = animationDuration.toInt(),
            scale = 3f,
            alpha = 0f,
        )

        _state.value = state
    }

    private suspend fun handleSetStateFinished() {
        val state = UIState(
            countdownText = "",
            animationDuration = 0,
            scale = 1f,
            alpha = 1f,
        )

        _state.value = state
        delay(timerOffset) // TODO: check if needed
    }
}
