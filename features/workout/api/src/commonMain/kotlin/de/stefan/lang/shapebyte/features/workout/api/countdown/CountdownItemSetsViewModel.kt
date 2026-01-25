package de.stefan.lang.shapebyte.features.workout.api.countdown

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class CountdownItemSetsViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {
    abstract override val state: StateFlow<UIState.Data<CountdownItemSetsViewData>>
    abstract fun intent(intent: CountdownItemSetsUIIntent)
}
