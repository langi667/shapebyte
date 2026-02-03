package de.stefan.lang.shapebyte.features.workout.contract.countdown

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.foundation.presentation.contract.state.UIState
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.StateFlow

public abstract class CountdownItemSetsViewModel(
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
) : BaseViewModel(logger, coroutineContextProvider) {
    abstract override val state: StateFlow<UIState.Data<CountdownItemSetsViewData>>
    public abstract fun intent(intent: CountdownItemSetsUIIntent)
}
