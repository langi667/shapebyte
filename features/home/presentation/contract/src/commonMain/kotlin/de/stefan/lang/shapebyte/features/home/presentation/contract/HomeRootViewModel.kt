package de.stefan.lang.shapebyte.features.home.presentation.contract

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import de.stefan.lang.utils.logging.contract.Logger

public abstract class HomeRootViewModel(
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
) : BaseViewModel(logger, coroutineContextProvider) {
    public abstract fun intent(intent: HomeRootUIIntent)
}
