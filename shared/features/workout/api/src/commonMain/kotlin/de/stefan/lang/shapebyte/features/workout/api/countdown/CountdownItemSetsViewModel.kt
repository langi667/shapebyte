package de.stefan.lang.shapebyte.features.workout.api.countdown

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.foundationUi.api.state.UIState
import de.stefan.lang.foundationUi.api.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class CountdownItemSetsViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
): BaseViewModel(logger, coroutineContextProvider) {
    abstract override val state: StateFlow<UIState.Data<CountdownItemSetsViewData>>
    abstract fun intent(intent: CountdownItemSetsUIIntent)
}
